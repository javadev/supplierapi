package com.cs.roomdbapi.web;

import com.cs.roomdbapi.annotation.IgnoreResponseBinding;
import com.cs.roomdbapi.dto.Notification;
import com.cs.roomdbapi.dto.Supplier;
import com.cs.roomdbapi.dto.SupplierWebhook;
import com.cs.roomdbapi.manager.NotificationManager;
import com.cs.roomdbapi.manager.SupplierManager;
import com.cs.roomdbapi.response.SuccessResponse;
import com.cs.roomdbapi.response.TokenResponse;
import com.cs.roomdbapi.utilities.AppUtils;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;
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

    private final SupplierManager supplierManager;

    private final NotificationManager notificationManager;

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
                    "Potentially could be used to avoid token expire, just need to refresh before expiration time."
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
            "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
    @GetMapping("/refresh-token")
    public TokenResponse refreshToken(HttpServletRequest req) {
        String newToken = supplierManager.refreshToken(req.getRemoteUser());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return new TokenResponse(newToken, validity.getTime() / 1000L);
    }

    @Operation(
            summary = "Save or update webhook url.",
            description = "This API can be used by authorized suppliers to save or update webhook url. <br/>" +
                    "This url should be available at execution time. Availability of url will be validated before save. <br/>" +
                    "Url will be used by notification system to send suppliers information after some entity was changed. <br/>" +
                    "This endpoint available **only for suppliers**, administrators should use other endpoint and provide supplier name parameter."
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
    // TODO make same for Admin, need to provide supplier name
    @PatchMapping("/webhook")
    public ResponseEntity<SupplierWebhook> webhookUpdate(
            @Valid @RequestBody SupplierWebhook webhookUpdate,
            HttpServletRequest req
    ) {
        SupplierWebhook webhook = supplierManager.saveWebhook(req.getRemoteUser(), webhookUpdate);

        return ResponseEntity.ok(webhook);
    }

    @Operation(
            summary = "Send test notification.",
            description = "This API can be used by authorized suppliers to execute notification test to supplier webhook url. <br/>" +
                    "Test notification will be in the same format as real one, but for test entity and with fake ids. <br/>" +
                    "This endpoint available **only for suppliers**, administrators should use other endpoint and provide supplier name parameter."
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
    // TODO make same for Admin, need to provide supplier name
    @GetMapping("/send-test-notification")
    public SuccessResponse<Boolean> sendTestNotification(HttpServletRequest req) {
        boolean sendResult = notificationManager.sendTestNotification(req.getRemoteUser());

        String message;
        if (sendResult) {
            message = AppUtils.TEST_NOTIFICATION_SUCCESS;
        } else {
            message = AppUtils.TEST_NOTIFICATION_FAIL;
        }

        return new SuccessResponse<>(sendResult, message, AppUtils.SUCCESS);
    }

    @Operation(
            summary = "Get supplier information. For supplier.",
            description = "This API can be used to get all information about supplier account. <br/>" +
                    "This endpoint available **only for suppliers**, administrators should use other endpoint and provide supplier name parameter."
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
    @GetMapping("/get-info")
    public ResponseEntity<Supplier> getInfo(HttpServletRequest req) {
        Supplier supplier = supplierManager.getByName(req.getRemoteUser());

        return ResponseEntity.ok(supplier);
    }

    @Operation(
            summary = "Get supplier information. For admin",
            description = "This API can be used to get all information about supplier account. <br/>" +
                    "This endpoint available **only for admin**, supplier should use other endpoint."
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN)")
    @GetMapping("/get-info/{name}")
    public ResponseEntity<Supplier> getInfoAdmin(
            @PathVariable
            @Parameter(description = "Supplier name. Required.")
            @Size(min = 1, max = 255)
                    String name
    ) {
        Supplier supplier = supplierManager.getByName(name);

        return ResponseEntity.ok(supplier);
    }

    // comment '@Hidden' annotation if you want to use this endpoint in Swagger
    @Hidden
    @Operation(
            summary = "Helper method to get encoded password for a provided password"
    )
    @GetMapping("/encode-password")
    public SuccessResponse<String> encodePassword(String password) {
        String encodedPassword = passwordEncoder.encode(password);

        return new SuccessResponse<>(encodedPassword, "Success password encode.", AppUtils.SUCCESS);
    }

    // comment '@Hidden' annotation if you want to use this endpoint in Swagger
    @Hidden
    @IgnoreResponseBinding
    @Operation(
            summary = "Test endpoint to receive notification from Supplier API.",
            description = "This API created just to check that out notification system works fine."
    )
    @PostMapping("/test-receive-webhook")
    public SuccessResponse<String> testReceiveWebhook(
            @Valid @RequestBody Notification notification
    ) {
        log.info("Received notification: " + notification.toString());

        return new SuccessResponse<>(notification, "Successful operation.", AppUtils.SUCCESS);
    }
}
