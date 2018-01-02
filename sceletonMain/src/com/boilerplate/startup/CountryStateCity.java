package com.boilerplate.startup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boilerplate.entity.City;
import com.boilerplate.entity.Country;
import com.boilerplate.entity.State;
import com.boilerplate.model.Status;
import com.boilerplate.repositories.CityRepository;
import com.boilerplate.repositories.CountryRepository;
import com.boilerplate.repositories.StateRepository;

@Service
public class CountryStateCity {

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	public void CountryStarter() {
		Country c = createCountry("Nepal");
		HashMap<Country, String> statesmap = new HashMap<Country, String>();

		statesmap.put(c,
				"Mechi,Koshi,Sagarmatha,Janakpur,Bagmati,Narayani,Gandaki,Lumbini,Dhaulagiri,Rapti,Karnali,Bheri,Seti,Mahakali");
		List<State> s = createState(statesmap);

		List<String> cityMap = new ArrayList<String>();
		cityMap.add("Ilam,Jhapa,Panchthar,Taplejung");
		cityMap.add("Bhojpur,Dhankuta,Morang,Sankhuwasabha,Sunsari,Terhathum");
		cityMap.add("Khotang,Okhaldhunga,Saptari,Siraha,Solukhumbu,Udayapur");
		cityMap.add("Dhanusa,Dholkha,Mahottari,Ramechhap,Sarlahi,Sindhuli");
		cityMap.add("Bhaktapur,Dhading,Kathmandu,Kavrepalanchok,Lalitpur,Nuwakot,Rasuwa,Sindhupalchok ");
		cityMap.add("Bara,Chitwan,Makwanpur,Parsa,Rautahat");
		cityMap.add("Gorkha,Kaski,Lamjung,Manang,Syangja,Tanahu");
		cityMap.add("Arghakhanchi,Gulmi,Kapilvastu,Nawalparasi,Palpa,Rupandehi");
		cityMap.add("Baglung,Mustang,Myagdi,Parbat");
		cityMap.add("Dang,Pyuthan,Rolpa,Rukum,Salyan");
		cityMap.add("Dolpa,Humla,Jumla,Kalikot,Mugu");
		cityMap.add("Banke,Bardiya,Dailekh,Jajarkot,Surkhet");
		cityMap.add("Achham,Bajhang,Bajura,Doti,Kailali");
		cityMap.add("Baitadi,Dadeldhura,Darchula,Kanchanpur");
		HashMap<State, String> citymap = new HashMap<State, String>();
		int start = 0;
		for (State ss : s) {
			citymap.put(ss, cityMap.get(start));
			start++;
		}
		createCity(citymap);

	}

	private Country createCountry(String country) {

		Country c = countryRepository.findByCountry(country);
		if (c == null) {
			c = new Country();
			c.setName("Nepal");
			c.setIsoThree("NPL");
			c.setIsoTwo("NP");
			c.setDialCode("977");
			c.setStatus(Status.Active);
		}
		return countryRepository.save(c);

	}

	private List<State> createState(HashMap<Country, String> statesmap) {
		List<State> st = new ArrayList<State>();
		for (Map.Entry<Country, String> entry : statesmap.entrySet()) {

			Country c = entry.getKey();
			String[] values = entry.getValue().split(",");
			for (int i = 0; i < values.length - 1; i++) {

				State s = stateRepository.findByState(values[i]);
				if (s == null) {
					s = new State();
					s.setCountry(c);
					s.setName(values[i]);
					s.setStatus(Status.Active);
					s = stateRepository.save(s);
					st.add(s);
				}

			}
		}
		return st;
	}

	private void createCity(HashMap<State, String> citymap) {
		for (Map.Entry<State, String> entry : citymap.entrySet()) {

			State s = entry.getKey();
			String[] values = entry.getValue().split(",");
			for (int i = 0; i < values.length - 1; i++) {

				City c = cityRepository.findByCity(values[i]);
				if (c == null) {
					c = new City();
					c.setState(s);
					c.setName(values[i]);
					c.setStatus(Status.Active);
					cityRepository.save(c);
				}

			}
		}
	}

}
