package io.sfinias.cli.toggl.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.smallrye.common.constraint.Nullable;
import java.math.BigDecimal;

@JsonInclude(Include.NON_NULL)
public class TogglProjectModel {

    @JsonProperty("id")
    public int id;

    @JsonProperty("workspace_id")
    public int workspaceId;

    @JsonProperty("is_private")
    public boolean isPrivate;

    @JsonProperty("template")
    @Nullable
    public Boolean template;

    @JsonProperty("color")
    public String color;

    @JsonProperty("recurring")
    public boolean recurring;

    @JsonProperty("active")
    public boolean active;

    @JsonProperty("created_at")
    public String createdAt;

    @JsonProperty("fixed_fee")
    @Nullable
    public BigDecimal fixedFee;

    @JsonProperty("billable")
    @Nullable
    public Boolean billable;

    @JsonProperty("client_id")
    @Nullable
    public Integer clientId;

    @JsonProperty("current_period")
    @Nullable
    public Object currentPeriod;

    @JsonProperty("auto_estimates")
    @Nullable
    public Boolean autoEstimates;

    @JsonProperty("at")
    public String at;

    @JsonProperty("rate")
    @Nullable
    public BigDecimal rate;

    @JsonProperty("actual_hours")
    @Nullable
    public Integer actualHours;

    @JsonProperty("rate_last_updated")
    public Object rateLastUpdated;

    @JsonProperty("name")
    public String name;

    @JsonProperty("estimated_hours")
    @Nullable
    public Integer estimatedHours;

    @JsonProperty("recurring_parameters")
    @Nullable
    public Object recurringParameters;

    @JsonProperty("currency")
    @Nullable
    public String currency;

    @JsonProperty("server_deleted_at")
    @Nullable
    public String serverDeletedAt;
}