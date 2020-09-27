package com.lievano.cc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lievano.cc.delegate.GameDelegate;
import com.lievano.cc.delegate.TimecontrolDelegate;
import com.lievano.cc.model.TsscTimecontrol;

@Controller
public class TimecontrolController {

	private TimecontrolDelegate timeDelegate;
	private GameDelegate gameDelegate;
	
	@Autowired
	public TimecontrolController(TimecontrolDelegate timeDelegate,GameDelegate gameDelegate) {
		this.timeDelegate=timeDelegate;
		this.gameDelegate=gameDelegate;
	}
	
	
	
	@GetMapping("/timecontrols/")
	public String indexTimeControls(Model model) {
		model.addAttribute("timecontrols",timeDelegate.findall());
		return "/timecontrols/index";
	}
	
	@GetMapping("/games/{gameid}/timecontrols")
	public String list(@PathVariable("gameid")long gameid,Model model) {
		
		
		
		return "timecontrols/list";
	}
	
	
	@GetMapping("/timecontrols/add")
	public String save(Model model) {
		TsscTimecontrol tsscTimecontrol=new TsscTimecontrol();
		model.addAttribute("tsscTimecontrol", tsscTimecontrol);
		model.addAttribute("games",gameDelegate.findAll());
		
		return "/timecontrols/add";
	}
	
	@PostMapping("/timecontrols/add")
	public String save(@Validated TsscTimecontrol tsscTimecontrol, BindingResult bindingResult,@RequestParam(value = "action",required = true)String action,Model model) {
		if(!action.equals("Cancel")) {
			if(bindingResult.hasErrors()) {
				model.addAttribute("games",gameDelegate.findAll());
				return "/timecontrols/add";
			}else {
				timeDelegate.save(tsscTimecontrol);
			}
		}
		return "redirect:/timecontrols/";
	}
	
	
}
