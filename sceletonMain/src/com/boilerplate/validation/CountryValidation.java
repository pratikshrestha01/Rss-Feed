package com.boilerplate.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boilerplate.api.ICountryApi;
import com.boilerplate.entity.Country;
import com.boilerplate.model.CountryDTO;
import com.boilerplate.model.error.CountryError;
import com.boilerplate.repositories.CountryRepository;


@Component
public class CountryValidation {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ICountryApi countryApi;

	@Autowired

	CountryRepository countryRepository;

	/*
	 * public CountryValidation(ICountryApi countryApi) { this.countryApi =
	 * countryApi; }
	 */

	public CountryError countryValidation(CountryDTO countryDTO) {

		CountryError countryError = new CountryError();
		boolean valid = true;

		if (countryDTO.getName() == null) {
			logger.debug("error at getName");
			countryError.setName("Name required");
			valid = false;
		} else if (countryDTO.getName().trim().equals("")) {
			logger.debug("error at getName");
			countryError.setName("Name Cannot be blank");
			valid = false;
		}

		if (countryDTO.getIsoTwo() == null) {
			logger.debug("error at iso two");
			countryError.setIsoTwo("Iso two required");
			valid = false;
		} else if (countryDTO.getIsoTwo().trim().equals("")) {
			logger.debug("Iso two null");
			countryError.setIsoTwo("Iso two Required");
			valid = false;
		}

		if (countryDTO.getIsoThree() == null) {
			logger.debug("error at iso three");
			countryError.setIsoThree("Iso three required");
			valid = false;
		} else if (countryDTO.getIsoThree().trim().equals("")) {
			logger.debug("Iso three null");
			countryError.setIsoThree("Iso three Required");
			valid = false;
		}

		if (countryDTO.getDialCode() == null) {
			logger.debug("error at dial code");
			countryError.setDialCode("Dial code Required");
			valid = false;
		} else if (countryDTO.getDialCode().trim().equals("")) {
			logger.debug("Dial code null");
			countryError.setDialCode("Dial Code Required");
			valid = false;
		}
		if (!(onlyContainsNumbers(countryDTO.getDialCode()))) {
			logger.debug("error at dial code");
			countryError.setDialCode("Invalid Dial code");
			valid = false;
		}else if(countryDTO.getDialCode().length() > 6){
			logger.debug("error at dial code");
			countryError.setDialCode("Invalid Dial code");
			valid = false;
		}

		if (countryDTO.getOperation().equals("edit")) {
			Country country = countryRepository.findCountryNameDem(countryDTO.getName());
			if (country != null) {
				if (!country.getName().equals(countryDTO.getName())) {
					String name = countryApi.findCountryName(countryDTO.getName());
					if (name != null) {
						logger.debug("country already exists");
						countryError.setName("Country Already Exists");
						valid = false;
					}
				}

				if (!country.getDialCode().equals(countryDTO.getDialCode())) {
					String dialCode = countryApi.fingCountryDialCode(countryDTO.getDialCode());
					if (dialCode != null) {
						logger.debug("dial code already exists");
						countryError.setDialCode("Dial code Already Exists");
						valid = false;
					}
				}

				if (!country.getIsoThree().equals(countryDTO.getIsoThree())) {
					String isoThree = countryApi.findCountryIsoThreee(countryDTO.getIsoThree());
					if (isoThree != null) {
						logger.debug("iso three already exists");
						countryError.setIsoThree("IsoThree Already Exists");
						valid = false;
					}
				}

				if (!country.getIsoTwo().equals(countryDTO.getIsoTwo())) {
					String isoTwo = countryApi.findCountryIsoTwo(countryDTO.getIsoTwo());
					if (isoTwo != null) {
						logger.debug("iso two already exists");
						countryError.setIsoTwo("IsoTwo Already Exists");
						valid = false;
					}
				}
			}

		} else {
			String name = countryApi.findCountryName(countryDTO.getName());
			if (name != null) {
				logger.debug("country already exists");
				countryError.setName("Country Already Exists");
				valid = false;
			}

			String dialCode = countryApi.fingCountryDialCode(countryDTO.getDialCode());
			if (dialCode != null) {
				logger.debug("dial code already exists");
				countryError.setDialCode("Dial code Already Exists");
				valid = false;
			}

			String isoThree = countryApi.findCountryIsoThreee(countryDTO.getIsoThree());
			if (isoThree != null) {
				logger.debug("iso three already exists");
				countryError.setIsoThree("IsoThree Already Exists");
				valid = false;
			}

			String isoTwo = countryApi.findCountryIsoTwo(countryDTO.getIsoTwo());
			if (isoTwo != null) {
				logger.debug("iso two already exists");
				countryError.setIsoTwo("IsoTwo Already Exists");
				valid = false;
			}
		}

		countryError.setValid(valid);
		return countryError;
	}

	private boolean onlyContainsNumbers(String dialCode) {
		try {
			Long.parseLong(dialCode);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

}
