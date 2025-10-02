package model;

import java.time.Instant;

public class RememberMeToken {
    private long userId;
    private String selector;
    private String validatorHash;
    private Instant expiresAt;

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
    public String getSelector() { return selector; }
    public void setSelector(String selector) { this.selector = selector; }
    public String getValidatorHash() { return validatorHash; }
    public void setValidatorHash(String validatorHash) { this.validatorHash = validatorHash; }
    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
}
