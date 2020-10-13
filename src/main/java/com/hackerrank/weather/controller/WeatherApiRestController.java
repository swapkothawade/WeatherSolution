package com.hackerrank.weather.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.repository.WeatherRepository;

@RestController
public class WeatherApiRestController {
	@Autowired
	private WeatherRepository weatherRepository;

	@PostMapping("/weather")
	public ResponseEntity<Weather> weather(@RequestBody Weather data) {
		// data.setId(getid());
		data = weatherRepository.save(data);
		return new ResponseEntity<Weather>(data, HttpStatus.CREATED);
	}

	@GetMapping("/weather")
	public ResponseEntity<?> weather() {
		List<Weather> weatherdat = weatherRepository.findAll();
		List<Weather> weatherdat1 = weatherdat.stream().sorted((w1, w2) -> w1.getId() - w2.getId())
				.collect(Collectors.toList());
		if (weatherdat1.size() > 0)
			return new ResponseEntity<List<Weather>>(weatherdat1, HttpStatus.OK);
		else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/weather/{id}")
	public ResponseEntity<?> weather(@PathVariable("id") int id) {
		Optional<Weather> weatherdat = weatherRepository.findById(id);
		if (weatherdat.isPresent())
			return new ResponseEntity<Weather>(weatherdat.get(), HttpStatus.OK);
		else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/weather/{id}")
	public ResponseEntity<?> deleteweather(@PathVariable("id") int id) {
		Optional<Weather> weatherdat = weatherRepository.findById(id);
		if (weatherdat.isPresent()) {
			weatherRepository.deleteById(id);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

	}

}
