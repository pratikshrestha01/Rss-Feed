package com.boilerplate.util;

import org.springframework.stereotype.Component;

import com.boilerplate.model.RestResponseDTO;
import com.boilerplate.model.mobile.ResponseStatus;

@Component
public class RestResponseUtil {

	public RestResponseDTO getResponseDto(ResponseStatus status, String message, Object detail) {
		RestResponseDTO restResponseDto = new RestResponseDTO();

		restResponseDto.setResponseStatus(status);
		restResponseDto.setMessage(message);
		restResponseDto.setDetail(detail);

		return restResponseDto;
	}
}
