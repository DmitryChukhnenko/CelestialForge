//package Dmitry.CelestialForge.Controllers;

// import java.util.Date;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import Dmitry.CelestialForge.Entities.Expense;
// import Dmitry.CelestialForge.Entities.User;
// import Dmitry.CelestialForge.Services.ExpenseService;
// import Dmitry.CelestialForge.Services.UserService;
// import Dmitry.CelestialForge.Session.SessionService;

// @Controller
// public class ExpenseController {
//     @Autowired
//     private ExpenseService expenseService;
//     @Autowired
//     private UserService userService;
//     @Autowired
//     private SessionService sessionService;

//     @GetMapping("/*")
//     public String all() {
//         return "redirect:/expenses";
//     }
    
//     @GetMapping("/login")
//     public String login(@RequestParam(value = "error", required = false) String error,
//                         @RequestParam(value = "logout", required = false) String logout,
//                         Model model) {
//         if (error != null) {
//             model.addAttribute("errorMessage", "Invalid username or password!");
//         }
//         if (logout != null) {
//             model.addAttribute("successMessage", "You have been logged out successfully.");
//         }
//         model.addAttribute("user", new User());
//         return "login";
//     }
    
//     @GetMapping("/registration")
//     public String registration(Model model) {
//         User user = new User();
//         model.addAttribute("user", user);
//         return "registration";
//     }

//     @PostMapping("/registration")
//     public String registration(Model model, @ModelAttribute User user) {
//         userService.addUser(user);
//         return "redirect:/login";
//     }

//     @GetMapping("/expenses")
//     public String showExpenses(Model model) {
//         model.addAttribute("expenses", expenseService.findByUserId(sessionService.getUser().getId()));
//         model.addAttribute("expense", new Expense());
//         return "index"; 
//     }

//     @PostMapping("/add")
//     public String addExpense(@ModelAttribute Expense expense, Model model) {
//         expense.setDate(new Date());
//         expense.setUser(sessionService.getUser());
//         expenseService.addExpense(expense);
//         return "redirect:/expenses";
//     }
// }
