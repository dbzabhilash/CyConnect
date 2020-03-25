package com.example.demo.models;
import javax.persistence.*;

@Entity
@Table(name = "info_page")
public class info_page {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "place_id")
	private Integer place_id; 


	@Column(name = "place_name")
	private String place_name;


	@Column(name = "x_coord")
	private double x_coord;

	@Column(name = "y_coord")
	private double y_coord;
	
	@Column(name = "study_eat_dorm")
	private Integer study_eat_dorm;
	
	public Integer getplace_id(){
		return place_id;
	}

	public String getplace_name(){
		return place_name;
	}
	
	public double getx_coord(){
		return x_coord;
	}
	
	public double gety_coord(){
		return y_coord;
	}
	
	public Integer getstudy_eat_dorm(){
		return study_eat_dorm;
	}
	
	public info_page() {
		
	}
    
	public info_page(Integer place_id,String place_name,double x_coord, double y_coord,Integer type) {
		this.place_id = place_id;
		this.place_name = place_name;
		this.x_coord = x_coord;
		this.y_coord = y_coord;
		this.study_eat_dorm = type;
	}
	
	
}
