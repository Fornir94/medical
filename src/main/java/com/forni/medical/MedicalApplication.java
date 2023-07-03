package com.forni.medical;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.forni.medical.model.Patient;
import com.forni.medical.service.PatientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@SpringBootApplication
public class MedicalApplication {

	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(MedicalApplication.class, args);}
}
