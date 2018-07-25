package uni.miskolc.ips.ilona.tracking.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uni.miskolc.ips.ilona.tracking.model.UserDetails;
import uni.miskolc.ips.ilona.tracking.persist.UserAndDeviceDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Tracking module
 *
 * @author Patrik
 */
@Controller
@RequestMapping(value = "/tracking")
public class TrackingController {

    private static Logger logger = LogManager.getLogger(TrackingController.class);

    @Autowired
    private UserAndDeviceDAO userAndDeviceDAO;

    @Autowired
    PasswordEncoder passwordEncoder;
    // @Autowired
    // private TrackingLoginService trackingLoginService;

    /**
     * This method gives back the index page of the tracking modul. Login or
     * registration.
     *
     * @return The tracking index jsp page.
     */
    @RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView loadingStartpage() {
        // logger.error("Tracking index page request.");
        // ModelAndView back = new ModelAndView("trackingIndex");
        ModelAndView mav = new ModelAndView();
        // mav.addObject("message",
        // SecurityContextHolder.getContext().getAuthentication().getName());
        // back.addObject("message", "Üzenetátadva!");
        mav.setViewName("trackingIndex");
        return mav;
    }

    /**
     * This method gives back the registration page. New user registration.
     *
     * @return
     */
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView loadLoginPage() {
        logger.error("Tracking login page request.");
        ModelAndView mav = new ModelAndView("trackingRegistration");
        mav.addObject("message", "");
        return mav;
    }

    /**
     * MAjd lesz itt valamit komment.. .valamikor... esetleg
     *
     * @return
     */
    @RequestMapping(value = "/main", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView loginProcess() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("authenticated", SecurityContextHolder.getContext().getAuthentication().getName());
        mav.addObject("rolenumber", SecurityContextHolder.getContext().getAuthentication().getAuthorities().size());
        return new ModelAndView("trackingMainpage");
    }

    @RequestMapping(value = "/logindecision", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView trackingLoginDecisionHandler(@ModelAttribute("username") String username,
                                                     @ModelAttribute("password") String password) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("username", username);
        mav.addObject("userid", password);
        mav.setViewName("Tracking/UserMainPage");

        return mav;
    }

    @RequestMapping(value = "/login1", method = {RequestMethod.GET, RequestMethod.POST})
    public String createLoginPage() {
        return "Tracking/TrackingLoginPage";
    }


    @RequestMapping(value = "/createuser1", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public List<UserDetails> userCreateProcess(@RequestParam(value = "roleadmin", required = false) String roleadmin,
                                               Model model, @ModelAttribute UserDetails user1) {
        System.out.println(user1.toString());
        System.out.println(roleadmin);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("Probalgatasok");
        List<UserDetails> userList = new ArrayList<UserDetails>();

        return userList;
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String logoutHandler() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("trackingIndex");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            mav.addObject("message", auth.getPrincipal().toString());
        } else {
            mav.addObject("message", "Nincs felhasznalo!");
        }
        mav.addObject("message", auth.getPrincipal().toString());
        return "redirect:/tracking/index";
    }

    @RequestMapping(value = "/probalgatasok", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView progaltasokToltese(@ModelAttribute("roleadmin") String roleadmin,
                                           @ModelAttribute("userid") String userid, Model model, HttpEntity<byte[]> httpEntity) {

        return new ModelAndView("Probalgatasok");
    }

    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserAndDeviceDAO getUserAndDeviceDAO() {
        return userAndDeviceDAO;
    }

}