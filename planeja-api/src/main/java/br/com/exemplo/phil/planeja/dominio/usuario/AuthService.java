package br.com.exemplo.phil.planeja.dominio.usuario;

import br.com.exemplo.phil.planeja.common.exceptions.UnauthorizedException;
import br.com.exemplo.phil.planeja.common.exceptions.ValidationException;
import br.com.exemplo.phil.planeja.common.validation.CampoInvalido;
import br.com.exemplo.phil.planeja.dominio.usuario.dto.AuthResponse;
import br.com.exemplo.phil.planeja.dominio.usuario.dto.LoginForm;
import br.com.exemplo.phil.planeja.dominio.usuario.dto.UsuarioCadastroForm;
import br.com.exemplo.phil.planeja.dominio.usuario.model.UsuarioEntity;
import br.com.exemplo.phil.planeja.infra.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder(10);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    public AuthResponse cadastrar(UsuarioCadastroForm form) {

        if(usuarioRepository.existsByLogin(form.login())){
            throw new ValidationException(List.of(new CampoInvalido("login", "Login já cadastrado.")));
        }

        String senhaCriptografada = encoder.encode(form.senha());

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome(form.nome());
        usuario.setLogin(form.login());
        usuario.setSenha(senhaCriptografada);

        usuarioRepository.save(usuario);

        return toAuthResponse(usuario);
    }

    public AuthResponse autenticar(LoginForm form) {

        final String mensagem = "Login ou senha inválidos.";

        UsuarioEntity usuario = usuarioRepository.findByLogin(form.login())
                .orElseThrow(() -> new UnauthorizedException(mensagem));

        boolean senhasBatem = encoder.matches(form.senha(), usuario.getSenha());

        if(!senhasBatem){
            throw new UnauthorizedException(mensagem);
        }

        return toAuthResponse(usuario);
    }

    private AuthResponse toAuthResponse(UsuarioEntity usuario) {

        var token = jwtService.gerarToken(usuario);
        var expiraEm = jwtService.getExpirationSeconds();

        return new AuthResponse(
                token,
                "Bearer",
                expiraEm,
                usuario.getNome()
        );
    }
}
