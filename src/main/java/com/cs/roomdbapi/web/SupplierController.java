package com.cs.roomdbapi.web;

import com.cs.roomdbapi.annotation.IgnoreResponseBinding;
import com.cs.roomdbapi.manager.SupplierManagerImpl;
import com.cs.roomdbapi.response.SuccessResponse;
import com.cs.roomdbapi.response.TokenResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Tag(
        name = "Suppliers",
        description = "API endpoints to get token." 
)
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/suppliers", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class SupplierController {

    private final SupplierManagerImpl supplierManager;

    private final PasswordEncoder passwordEncoder;

    @Value("${security.jwt.token.expire-length:3600000}") // Default is 1 hour
    private long validityInMilliseconds;

    @IgnoreResponseBinding
    @Operation(
            summary = "Common call and get token base on clientId and clientSecret.",
            description = "API endpoint to generate new token."
    )
    @PostMapping("/get-token")
    public TokenResponse getToken(
            @RequestParam(name = "supplierId") String supplierId, //
            @RequestParam(name = "supplierSecret") String supplierSecret) {

        String token = supplierManager.signIn(supplierId, supplierSecret);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return new TokenResponse(token, validity.getTime() / 1000L);
    }

    @IgnoreResponseBinding
    @Operation(
            summary = "Refresh token.",
            description = "This API can be used by authorized users to get new token. <br/>" +
                    "Potentially could be used to avoid token expire, just need to refresh before experation time."
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
            "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
    @GetMapping("/refresh")
    public TokenResponse refresh(HttpServletRequest req) {
        String newToken = supplierManager.refresh(req.getRemoteUser());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return new TokenResponse(newToken, validity.getTime() / 1000L);
    }

    @Hidden // comment if you want to use this endpoint in Swagger
    @GetMapping("/encode-password")
    public SuccessResponse<String> encodePassword(String password) {
        String encodedPassword = passwordEncoder.encode(password);

        log.info("Encode password called.");

        return new SuccessResponse<>(encodedPassword, "Success password encode.");
    }

//  @PostMapping("/signup")
//  @ApiResponses(value = {//
//      @ApiResponse(code = 400, message = "Something went wrong"), //
//      @ApiResponse(code = 403, message = "Access denied"), //
//      @ApiResponse(code = 422, message = "Username is already in use")})
//  public String signup(@ApiParam("Signup User") @RequestBody UserDataDTO user) {
//  public String signup(@ApiParam("Signup User") @RequestBody UserDataDTO user) {
//    return userService.signup(modelMapper.map(user, User.class));
//  }

//  @DeleteMapping(value = "/{username}")
//  @PreAuthorize("hasRole('ROLE_ADMIN')")
//  @ApiOperation(value = "${UserController.delete}", authorizations = { @Authorization(value="apiKey") })
//  @ApiResponses(value = {//
//      @ApiResponse(code = 400, message = "Something went wrong"), //
//      @ApiResponse(code = 403, message = "Access denied"), //
//      @ApiResponse(code = 404, message = "The user doesn't exist"), //
//      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
//  public String delete(@ApiParam("Username") @PathVariable String username) {
//    userService.delete(username);
//    return username;
//  }
//
//  @GetMapping(value = "/{username}")
//  @PreAuthorize("hasRole('ROLE_ADMIN')")
//  @ApiOperation(value = "${UserController.search}", response = UserResponseDTO.class, authorizations = { @Authorization(value="apiKey") })
//  @ApiResponses(value = {//
//      @ApiResponse(code = 400, message = "Something went wrong"), //
//      @ApiResponse(code = 403, message = "Access denied"), //
//      @ApiResponse(code = 404, message = "The user doesn't exist"), //
//      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
//  public UserResponseDTO search(@ApiParam("Username") @PathVariable String username) {
//    return modelMapper.map(userService.search(username), UserResponseDTO.class);
//  }
//
//  @GetMapping(value = "/me")
//  @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
//  @ApiOperation(value = "${UserController.me}", response = UserResponseDTO.class, authorizations = { @Authorization(value="apiKey") })
//  @ApiResponses(value = {//
//      @ApiResponse(code = 400, message = "Something went wrong"), //
//      @ApiResponse(code = 403, message = "Access denied"), //
//      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
//  public UserResponseDTO whoami(HttpServletRequest req) {
//    return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
//  }

}
