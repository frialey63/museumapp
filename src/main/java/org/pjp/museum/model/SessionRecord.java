package org.pjp.museum.model;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SessionRecord {

    private static final ZoneOffset UTC = ZoneOffset.UTC;

	@Id
    private String uuid;

    @NotBlank
    private String ipAddress;
    
    @NotBlank
    private String browserApplication;
    
    @NotNull
    private MobileType mobileType;

    @NotNull
    private Instant startTime;

    private Instant finishTime;
    
    private Set<String> tailScans = new HashSet<>();

    private Set<String> tailPicks = new HashSet<>();

	public SessionRecord(String uuid, @NotBlank String ipAddress, @NotBlank String browserApplication, @NotNull MobileType mobileType, @NotNull Instant startTime) {
		super();
		this.uuid = uuid;
		this.ipAddress = ipAddress;
		this.browserApplication = browserApplication;
		this.mobileType = mobileType;
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

	public String getIpAddress() {
		return ipAddress;
	}

	public AddressType getAddressType(String secureAddresses) {
		return AddressType.getAddressType(secureAddresses, ipAddress);
	}

	public String getBrowserApplication() {
		return browserApplication;
	}

	public MobileType getMobileType() {
		return mobileType;
	}

	public Instant getStartTime() {
		return startTime;
	}
	
	public Set<String> getTailScans() {
		return tailScans;
	}
 
	public Set<String> getTailScansByTailNumber() {
		return tailScans.stream().map(str -> str.split("\\:")[1]).collect(Collectors.toSet());
	}
 
	public void setTailScans(Set<String> tailScans) {
		this.tailScans = tailScans;
	}

	public void addTailScan(String tailNumber) {
		String entry = String.format("%d:%s", Instant.now().toEpochMilli(), tailNumber);
		tailScans.add(entry);
	}

	public Set<String> getTailPicks() {
		return tailPicks;
	}

	public Set<String> getTailPicksByTailNumber() {
		return tailPicks.stream().map(str -> str.split("\\:")[1]).collect(Collectors.toSet());
	}

	public void setTailPicks(Set<String> tailPicks) {
		this.tailPicks = tailPicks;
	}

	public void addTailPick(String tailNumber) {
		String entry = String.format("%d:%s", Instant.now().toEpochMilli(), tailNumber);
		tailPicks.add(entry);
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
    
    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SessionRecord [uuid=");
		builder.append(uuid);
		builder.append(", ipAddress=");
		builder.append(ipAddress);
		builder.append(", mobileType=");
		builder.append(mobileType);
		builder.append(", startTime=");
		builder.append(startTime);
		builder.append(", finishTime=");
		builder.append(finishTime);
		builder.append("]");
		return builder.toString();
	}

	public Set<Period> getPeriods() {
    	Set<Period> result = new HashSet<>();
    	
    	LocalDate now = LocalDate.now();
    	
		Instant startOfDay = now.atStartOfDay().toInstant(UTC);    	
		Instant startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay().toInstant(UTC);
		Instant startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay().toInstant(UTC);
		Instant startOfYear = now.with(TemporalAdjusters.firstDayOfYear()).atStartOfDay().toInstant(UTC);
		
    	if (startTime.isAfter(startOfDay)) {
    		result.add(Period.TODAY);
    	}
    	
    	if (startTime.isAfter(startOfWeek)) {
    		result.add(Period.WEEK);
    	}
    	
    	if (startTime.isAfter(startOfMonth)) {
    		result.add(Period.MONTH);
    	}
    	
    	if (startTime.isAfter(startOfYear)) {
    		result.add(Period.YEAR);
    	}
    	
    	result.add(Period.ALL);
    	
    	return result;
    }
    
	public Set<String> getTailNumbers(boolean scan) {
		return scan ? getTailScansByTailNumber() : getTailPicksByTailNumber();
	}
 
}
