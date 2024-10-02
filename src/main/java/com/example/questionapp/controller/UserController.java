package com.example.questionapp.controller;

import com.example.questionapp.model.User;
import com.example.questionapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Şifreyi şifrele
        userService.registerUser(user);  // Kullanıcıyı kaydet
        return "redirect:/login";  // Kayıttan sonra giriş sayfasına yönlendir
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        try {
            // Kullanıcı adı ile kullanıcıyı bul
            User user = (User) userService.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new Exception("Kullanıcı bulunamadı"));

            // Şifre doğrulama
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new Exception("Geçersiz şifre");
            }

            // Başarılı giriş işlemi (JWT token üretimi burada olabilir)
            return ResponseEntity.ok("Giriş başarılı");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
