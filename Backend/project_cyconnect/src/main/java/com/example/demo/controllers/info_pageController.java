package com.example.demo.controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.example.demo.repositories.LoginRepository;
import com.example.demo.repositories.info_pageRepository;
import com.example.demo.services.LoginService;
import com.example.demo.models.Login;
import com.example.demo.models.info_page;
import com.example.demo.services.Locationhelper;

import java.util.*;

@RestController
public class info_pageController {

	@Autowired
	private info_pageRepository info_pageRepo;
	private final Logger infoLog = LoggerFactory.getLogger(info_pageController.class);

	@GetMapping("/info_page/{id}")
	Optional<info_page> getId(@PathVariable Integer id) {
		return info_pageRepo.findById(id);
	}

	@GetMapping("/info_page/dorm/{dormName}")
	info_page getDorminfo(@PathVariable String dormName) {
		return info_pageRepo.findByDorm(dormName);
	}

	@GetMapping("/info_page/find/{i}")
	info_page count(@PathVariable int i) {
		return info_pageRepo.findByPlace_id((int)i);
	}


	@GetMapping(path = "/info_page/sending/{dorm}") 	//finds shortest distance place to study
	public String sendPlaces(@PathVariable String dorm) {


		info_page currDorm = getDorminfo(dorm);
		info_page tempDorm = null;
		int currDormID=currDorm.getplace_id();
		double x, y;
		double dorm_x = currDorm.getx_coord();
		double dorm_y = currDorm.gety_coord();
		int limit =(int) info_pageRepo.count();
		int i=1;
		String retVal="false";
		double smallest=Double.MAX_VALUE;
		for(i=1;i<=limit;i++) {
			if(i==currDormID) break;
			
				tempDorm=info_pageRepo.findByPlace_id((int)i);

				x= tempDorm.getx_coord();
				y = tempDorm.gety_coord();
				double dist=Math.sqrt(Math.pow((x-dorm_x), 2)+Math.pow((y-dorm_y), 2)); //distance from current to a study place

				if(dist<smallest) {
					smallest = dist;
					retVal=tempDorm.getplace_name();
				}

		}
		return retVal;
	}	
	
	@PostMapping(path = "/info_page/toeat")
	public Map toeat(@RequestBody Locationhelper loc) {
		
		double longi = loc.getLongitude();
		double lati = loc.getLatitude();
		double x,y;
		String closest = "";
		
		//int count = (int)info_pageRepo.count();
		double smallest = Double.MAX_VALUE;
		
		List<info_page> list = info_pageRepo.findByTypeofPlace(1);
		int size = list.size();
		int count = 0;
		while(count<size) {
			
			x = list.get(count).getx_coord();
			y = list.get(count).gety_coord();
			double dist=Math.sqrt(Math.pow((x-lati), 2)+Math.pow((y-longi), 2));
			
			if(dist<smallest) {
				smallest = dist;
				closest=list.get(count).getplace_name();
			}
		
			count++;
			
		}
		
		
		
		//String s = String.valueOf(longi) + "and" + String.valueOf(lati) ;
		Map<String, String> exists = Collections.singletonMap("Response",closest);
		
		return exists;
		
	}
	
	@PostMapping(path = "/info_page/tostudy")
	public Map tostudy(@RequestBody Locationhelper loc) {
		
		double longi = loc.getLongitude();
		double lati = loc.getLatitude();
		double x,y;
		String closest = "";
		
		//int count = (int)info_pageRepo.count();
		double smallest = Double.MAX_VALUE;
		
		List<info_page> list = info_pageRepo.findByTypeofPlace(0);
		int size = list.size();
		int count = 0;
		while(count<size) {
			
			x = list.get(count).getx_coord();
			y = list.get(count).gety_coord();
			double dist=Math.sqrt(Math.pow((x-lati), 2)+Math.pow((y-longi), 2));
			
			if(dist<smallest) {
				smallest = dist;
				closest=list.get(count).getplace_name();
			}
		
			count++;
			
		}
		
		//String s = String.valueOf(longi) + "and" + String.valueOf(lati) ;
		Map<String, String> exists = Collections.singletonMap("Response",closest);
		
		return exists;
		
	}
	
	@PostMapping(path = "/info_page/todorm")
	public Map todorm(@RequestBody Locationhelper loc) {
		
		double longi = loc.getLongitude();
		double lati = loc.getLatitude();
		double x,y;
		String closest = "";
		
		//int count = (int)info_pageRepo.count();
		double smallest = Double.MAX_VALUE;
		
		List<info_page> list = info_pageRepo.findByTypeofPlace(2);
		int size = list.size();
		int count = 0;
		while(count<size) {
			
			x = list.get(count).getx_coord();
			y = list.get(count).gety_coord();
			double dist=Math.sqrt(Math.pow((x-lati), 2)+Math.pow((y-longi), 2));
			
			if(dist<smallest) {
				smallest = dist;
				closest=list.get(count).getplace_name();
			}
		
			count++;
			
		}
		
		
		
		//String s = String.valueOf(longi) + "and" + String.valueOf(lati) ;
		Map<String, String> exists = Collections.singletonMap("Response",closest);
		
		return exists;
		
	}

}
