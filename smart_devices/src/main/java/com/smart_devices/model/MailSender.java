package com.smart_devices.model;

import lombok.Builder;

@Builder
public record MailSender(String to, String subject, String content, String otp) {
}