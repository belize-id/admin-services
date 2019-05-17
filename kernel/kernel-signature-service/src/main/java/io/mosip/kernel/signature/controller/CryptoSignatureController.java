package io.mosip.kernel.signature.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.mosip.kernel.core.http.RequestWrapper;
import io.mosip.kernel.core.http.ResponseFilter;
import io.mosip.kernel.core.http.ResponseWrapper;
import io.mosip.kernel.core.signatureutil.model.SignatureResponse;
import io.mosip.kernel.signature.dto.PublicKeyRequestDto;
import io.mosip.kernel.signature.dto.SignResponseRequestDto;
import io.mosip.kernel.signature.dto.TimestampRequestDto;
import io.mosip.kernel.signature.dto.ValidatorResponseDto;
import io.mosip.kernel.signature.service.CryptoSignatureService;

/**
 * 
 * @author Uday Kumar
 * @since 1.0.0
 *
 */
@RestController
@CrossOrigin
public class CryptoSignatureController {
	/**
	 * Crypto signature Service field with functions related to signature
	 */
	@Autowired
	CryptoSignatureService service;

	/**
	 * Function to sign response
	 * 
	 * @param requestDto {@link SignResponseRequestDto} having required fields.
	 * @return The {@link SignatureResponse}
	 */
	@PreAuthorize("hasAnyRole('INDIVIDUAL','REGISTRATION_PROCESSOR','ID_AUTHENTICATION','TEST')")
	@ResponseFilter
	@PostMapping(value = "/sign")
	public ResponseWrapper<SignatureResponse> signResponse(
			@RequestBody @Valid RequestWrapper<SignResponseRequestDto> requestDto) {
		ResponseWrapper<SignatureResponse> response = new ResponseWrapper<>();
		response.setResponse(service.signResponse(requestDto.getRequest()));
		return response;
	}

	/**
	 * Function to validate with public key
	 * 
	 * @param validateWithPublicKeyRequestDto {@link AuditRequestDto} having
	 *                                        required fields for auditing
	 * @return The {@link Boolean} having the value
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	@PreAuthorize("hasAnyRole('INDIVIDUAL','REGISTRATION_PROCESSOR','ID_AUTHENTICATION','TEST')")
	@ResponseFilter
	@PostMapping(value = "/public/validate")
	public ResponseWrapper<ValidatorResponseDto> validateWithPublicKey(
			@RequestBody @Valid RequestWrapper<PublicKeyRequestDto> validateWithPublicKeyRequestDto)
			throws InvalidKeySpecException, NoSuchAlgorithmException {
		ResponseWrapper<ValidatorResponseDto> response = new ResponseWrapper<>();
		response.setResponse(service.validateWithPublicKey(validateWithPublicKeyRequestDto.getRequest()));
		return response;
	}

	@PreAuthorize("hasAnyRole('INDIVIDUAL','REGISTRATION_PROCESSOR','ID_AUTHENTICATION','TEST')")
	@ResponseFilter
	@PostMapping(value = "/validate")
	public ResponseWrapper<ValidatorResponseDto> validate(
			@RequestBody @Valid RequestWrapper<TimestampRequestDto> timestampRequestDto)
			throws InvalidKeySpecException, NoSuchAlgorithmException {
		ResponseWrapper<ValidatorResponseDto> response = new ResponseWrapper<>();
		response.setResponse(service.validate(timestampRequestDto.getRequest()));
		return response;
	}
}
