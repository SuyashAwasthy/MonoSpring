package com.techlabs.app.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course_table")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

	@Id
	@GeneratedValue
	private long id;
	
	private String title;
	private int modules;
	private double fee;
	
	@ManyToMany(mappedBy = "courses")
	@JsonBackReference
	private Set<Student> students = new HashSet<>();
	
	public Course() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getModules() {
		return modules;
	}

	public void setModules(int modules) {
		this.modules = modules;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(fee, id, modules, students, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return Double.doubleToLongBits(fee) == Double.doubleToLongBits(other.fee) && id == other.id
				&& modules == other.modules && Objects.equals(students, other.students)
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", title=" + title + ", modules=" + modules + ", fee=" + fee + ", students="
				+ students + "]";
	}
	
	
}
