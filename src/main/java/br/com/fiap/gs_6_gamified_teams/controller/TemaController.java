package br.com.fiap.gs_6_gamified_teams.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("temas")
public class TemaController {

    @GetMapping("/info")
    public Map<String, String> getInfo() {
        return Map.of(
            "tema", "Soluções gamificadas para motivação em equipes híbridas",
            "membro1", "Renata Almeira Lima",
            "membro2", "Rick Alves Domingues",
            "descricao", "Uso de elementos de jogo para engajar, reconhecer e motivar equipes que atuam no modelo híbrido."
        );
    }

}
