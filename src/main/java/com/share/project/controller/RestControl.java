package com.share.project.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.share.project.model.FirstModel;
import com.share.project.model.SecondModel;
import com.share.project.model.ViewClass;
import com.share.project.repo.FirstDetails;
import com.share.project.repo.SecondDetails;
import com.share.project.service.DetailsService;

@RestController
@RequestMapping("/api")
public class RestControl {
	
	@Autowired
	DetailsService detailsService;
	
	@Autowired
	FirstDetails firstDetails;
	
	@Autowired
	SecondDetails secondDetails;
	
	@PostMapping("/firstModel")
	public String addMemberFirst(@RequestBody FirstModel model) {
	    firstDetails.save(model);
	    return "Suscess";
	}
	
	@PostMapping("/secondModel")
	public String addMemberSecond(@RequestBody SecondModel model) {
	    secondDetails.save(model);
	    return "Suscess";
	}
	
	@GetMapping("/fetch{?query}")
    public List<ViewClass> getTheFileType(@RequestParam("query") String query) throws IOException {
		System.out.println(query);
        return detailsService.getDetails(query);
    }
	
}
