package com.SupportApplication.SupportApp.Common.DTO;

import java.time.LocalDateTime;

public record ErrorResponse(String message, int code, LocalDateTime timestamp) {}
