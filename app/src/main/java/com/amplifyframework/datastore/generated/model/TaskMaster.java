package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the TaskMaster type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "TaskMasters")
@Index(name = "byTeam", fields = {"teamID","title"})
public final class TaskMaster implements Model {
  public static final QueryField ID = field("TaskMaster", "id");
  public static final QueryField TITLE = field("TaskMaster", "title");
  public static final QueryField BODY = field("TaskMaster", "body");
  public static final QueryField STATUS = field("TaskMaster", "status");
  public static final QueryField TEAM = field("TaskMaster", "teamID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String") String body;
  private final @ModelField(targetType="Status", isRequired = true) Status status;
  private final @ModelField(targetType="Team", isRequired = true) @BelongsTo(targetName = "teamID", type = Team.class) Team team;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getBody() {
      return body;
  }
  
  public Status getStatus() {
      return status;
  }
  
  public Team getTeam() {
      return team;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private TaskMaster(String id, String title, String body, Status status, Team team) {
    this.id = id;
    this.title = title;
    this.body = body;
    this.status = status;
    this.team = team;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      TaskMaster taskMaster = (TaskMaster) obj;
      return ObjectsCompat.equals(getId(), taskMaster.getId()) &&
              ObjectsCompat.equals(getTitle(), taskMaster.getTitle()) &&
              ObjectsCompat.equals(getBody(), taskMaster.getBody()) &&
              ObjectsCompat.equals(getStatus(), taskMaster.getStatus()) &&
              ObjectsCompat.equals(getTeam(), taskMaster.getTeam()) &&
              ObjectsCompat.equals(getCreatedAt(), taskMaster.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), taskMaster.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getBody())
      .append(getStatus())
      .append(getTeam())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("TaskMaster {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("body=" + String.valueOf(getBody()) + ", ")
      .append("status=" + String.valueOf(getStatus()) + ", ")
      .append("team=" + String.valueOf(getTeam()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TitleStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static TaskMaster justId(String id) {
    return new TaskMaster(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      title,
      body,
      status,
      team);
  }
  public interface TitleStep {
    StatusStep title(String title);
  }
  

  public interface StatusStep {
    TeamStep status(Status status);
  }
  

  public interface TeamStep {
    BuildStep team(Team team);
  }
  

  public interface BuildStep {
    TaskMaster build();
    BuildStep id(String id);
    BuildStep body(String body);
  }
  

  public static class Builder implements TitleStep, StatusStep, TeamStep, BuildStep {
    private String id;
    private String title;
    private Status status;
    private Team team;
    private String body;
    @Override
     public TaskMaster build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new TaskMaster(
          id,
          title,
          body,
          status,
          team);
    }
    
    @Override
     public StatusStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public TeamStep status(Status status) {
        Objects.requireNonNull(status);
        this.status = status;
        return this;
    }
    
    @Override
     public BuildStep team(Team team) {
        Objects.requireNonNull(team);
        this.team = team;
        return this;
    }
    
    @Override
     public BuildStep body(String body) {
        this.body = body;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String title, String body, Status status, Team team) {
      super.id(id);
      super.title(title)
        .status(status)
        .team(team)
        .body(body);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder status(Status status) {
      return (CopyOfBuilder) super.status(status);
    }
    
    @Override
     public CopyOfBuilder team(Team team) {
      return (CopyOfBuilder) super.team(team);
    }
    
    @Override
     public CopyOfBuilder body(String body) {
      return (CopyOfBuilder) super.body(body);
    }
  }
  
}
