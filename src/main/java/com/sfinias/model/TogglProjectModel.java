package com.sfinias.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.smallrye.common.constraint.Nullable;
import java.math.BigDecimal;

@JsonInclude(Include.NON_NULL)
public class TogglProjectModel {

    @JsonProperty("id")
    private int id;

    @JsonProperty("workspace_id")
    private int workspaceId;

    @JsonProperty("is_private")
    private boolean isPrivate;

    @JsonProperty("template")
    @Nullable
    private Boolean template;

    @JsonProperty("color")
    private String color;

    @JsonProperty("recurring")
    private boolean recurring;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("fixed_fee")
    @Nullable
    private BigDecimal fixedFee;

    @JsonProperty("billable")
    @Nullable
    private Boolean billable;

    @JsonProperty("client_id")
    @Nullable
    private Integer clientId;

    @JsonProperty("current_period")
    @Nullable
    private Object currentPeriod;

    @JsonProperty("auto_estimates")
    @Nullable
    private Boolean autoEstimates;

    @JsonProperty("at")
    private String at;

    @JsonProperty("rate")
    @Nullable
    private BigDecimal rate;

    @JsonProperty("actual_hours")
    @Nullable
    private Integer actualHours;

    @JsonProperty("rate_last_updated")
    private Object rateLastUpdated;

    @JsonProperty("name")
    private String name;

    @JsonProperty("estimated_hours")
    @Nullable
    private Integer estimatedHours;

    @JsonProperty("recurring_parameters")
    @Nullable
    private Object recurringParameters;

    @JsonProperty("currency")
    @Nullable
    private String currency;

    @JsonProperty("server_deleted_at")
    @Nullable
    private String serverDeletedAt;

    public int getId() {

        return id;
    }

    public int getWorkspaceId() {

        return workspaceId;
    }

    public boolean isPrivate() {

        return isPrivate;
    }

    public Boolean getTemplate() {

        return template;
    }

    public String getColor() {

        return color;
    }

    public boolean isRecurring() {

        return recurring;
    }

    public boolean isActive() {

        return active;
    }

    public String getCreatedAt() {

        return createdAt;
    }

    public BigDecimal getFixedFee() {

        return fixedFee;
    }

    public Boolean getBillable() {

        return billable;
    }

    public Integer getClientId() {

        return clientId;
    }

    public Object getCurrentPeriod() {

        return currentPeriod;
    }

    public Boolean getAutoEstimates() {

        return autoEstimates;
    }

    public String getAt() {

        return at;
    }

    public BigDecimal getRate() {

        return rate;
    }

    public Integer getActualHours() {

        return actualHours;
    }

    public Object getRateLastUpdated() {

        return rateLastUpdated;
    }

    public String getName() {

        return name;
    }

    public Integer getEstimatedHours() {

        return estimatedHours;
    }

    public Object getRecurringParameters() {

        return recurringParameters;
    }

    public String getCurrency() {

        return currency;
    }

    public String getServerDeletedAt() {

        return serverDeletedAt;
    }
}