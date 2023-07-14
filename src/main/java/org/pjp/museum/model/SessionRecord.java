package org.pjp.museum.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SessionRecord {

    @Id
    private String uuid;

    @NotBlank
    private String browserApplication;

    @NotNull
    private Instant startTime;

    private Instant finishTime;
    
    private Set<String> tailScans = new HashSet<>();

    private Set<String> tailPicks = new HashSet<>();

	public SessionRecord(String uuid, @NotBlank String browserApplication, @NotNull Instant startTime) {
		super();
		this.uuid = uuid;
		this.browserApplication = browserApplication;
		this.startTime = startTime;
	}

	public Instant getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Instant finishTime) {
		this.finishTime = finishTime;
	}

	public String getUuid() {
		return uuid;
	}

	public String getBrowserApplication() {
		return browserApplication;
	}

	public Instant getStartTime() {
		return startTime;
	}
	
	public Set<String> getTailScans() {
		return tailScans;
	}
 
	public void setTailScans(Set<String> tailScans) {
		this.tailScans = tailScans;
	}

	public void addTailScan(String tailNumber) {
		tailScans.add(tailNumber);
	}

	public Set<String> getTailPicks() {
		return tailPicks;
	}

	public void setTailPicks(Set<String> tailPicks) {
		this.tailPicks = tailPicks;
	}

	public void addTailPick(String tailNumber) {
		tailPicks.add(tailNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SessionRecord other = (SessionRecord) obj;
		return Objects.equals(uuid, other.uuid);
	}
    
    
}
