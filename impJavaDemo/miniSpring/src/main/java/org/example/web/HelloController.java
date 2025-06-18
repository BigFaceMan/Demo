package org.example.web;


import org.example.Component;

@Controller
@Component
public class HelloController {
    @RequestMapping("/json")
    @ResponseBody
    public User testJson(@Param("name") String name, @Param("age")Integer age) {
        return new User(name,  age);
    }
    @RequestMapping("/mv")
    public ModelAndlView testModelAndView(@Param("name") String name, @Param("age")Integer age) {
        ModelAndlView mv = new ModelAndlView();
        mv.setView("index.html");
        mv.getContext().put("name", name);
        return mv;
    }
    @RequestMapping("/html")
    public String testHtml() {
        return "<h1>test Html</h1>";
    }
}
