package br.com.exemplo.phil.planeja.dominio.dashboard;

import br.com.exemplo.phil.planeja.dominio.dashboard.dto.Dashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public Dashboard obterDashBoardMesAtual(){
        return dashboardService.obterDashboardMesAtual();
    }

}
