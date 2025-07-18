package com.example.tienda.controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class AyudaController {

    @GetMapping("/contactenos")
    public String Registro() {
        return "redirect:https://wa.me/51956531880";
    }

    @GetMapping("/index")
    public String Volver() {
        return "forward:/index.html";
    }

    @GetMapping("/ayuda")
    public String ayudar() {
        return "forward:/ayuda.html";
    }

}
