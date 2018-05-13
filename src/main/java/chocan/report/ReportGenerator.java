package chocan.report;

import chocan.service.IProviderServiceDirectory;
import chocan.service.IServiceRecordDatabase;
import chocan.service.Service;
import chocan.service.ServiceRecord;
import chocan.users.*;
import chocan.utils.DateUtils;
import chocan.utils.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class ReportGenerator {

    private static final String DATE_FORMAT = "MM-dd-uuuu";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final String DATE_TIME_FORMAT = "MM-dd-uuuu HH:mm:ss";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    private final IUserDatabase<Member> memberDatabase;
    private final IUserDatabase<Provider> providerDatabase;
    private final IProviderServiceDirectory providerServiceDirectory;
    private final IServiceRecordDatabase serviceRecordDatabase;

    /**
     * Creates a new report generator using the given databases and directories.
     * @param memberDatabase
     * @param providerDatabase
     * @param providerServiceDirectory
     * @param serviceRecordDatabase
     */
    public ReportGenerator(final IUserDatabase<Member> memberDatabase,
                           final IUserDatabase<Provider> providerDatabase,
                           final IProviderServiceDirectory providerServiceDirectory,
                           final IServiceRecordDatabase serviceRecordDatabase) {
        this.memberDatabase = memberDatabase;
        this.providerDatabase = providerDatabase;
        this.providerServiceDirectory = providerServiceDirectory;
        this.serviceRecordDatabase = serviceRecordDatabase;
    }

    /**
     * Generates a member's report for the week.
     * @param member The member to generate the report for.
     * @return A string containing the report contents.
     */
    public String forMember(final Member member) {
        final StringBuilder sb = new StringBuilder();
        // Print member information
        sb.append("Name: ").append(member.name).append('\n');
        sb.append("Account number: ").append(member.id).append('\n');
        sb.append("Street address: ").append(member.address).append('\n');
        sb.append("City: ").append(member.city).append('\n');
        sb.append("State: ").append(member.state).append('\n');
        sb.append("Zip: ").append(member.zip).append('\n');
        // Get current week date time range
        final LocalDateTime[] weekRange = DateUtils.getWeekRange();
        // Query for recorded services provided
        final List<ServiceRecord> memberServiceRecords = this.serviceRecordDatabase
                .get(weekRange[0], weekRange[1])
                .filter(record -> record.memberID == member.id)
                .sorted(Comparator.comparing(record1 -> record1.serviceDate))
                .collect(Collectors.toList());
        // Print service record information
        final int idStringLength = Integer.toString(memberServiceRecords.size()).length();
        int i = 1;
        for (final ServiceRecord record : memberServiceRecords) {
            final Service service = this.providerServiceDirectory.get(record.serviceCode);
            final Provider provider = this.providerDatabase.get(record.providerID);
            final String padding = StringUtils.padLeft("", idStringLength);
            sb.append(StringUtils.padLeft(Integer.toString(i), idStringLength)).append(". ").append(service != null ? service.name : "Unknown service").append('\n');
            sb.append(padding).append("| by ").append(provider != null ? provider.name : "Unknown provider").append('\n');
            sb.append(padding).append("` on ").append(record.serviceDate.format(DATE_FORMATTER)).append('\n');
            i++;
        }
        // Return finished member report
        return sb.toString();
    }

    /**
     * Generates a provider's report for the week.
     * @param provider The provider to generate the report for.
     * @return A string containing the report contents.
     */
    public String forProvider(final Provider provider) {
        final StringBuilder sb = new StringBuilder();
        // Print provider information
        sb.append("Name: ").append(provider.name).append('\n');
        sb.append("Account number: ").append(provider.id).append('\n');
        sb.append("Street address: ").append(provider.address).append('\n');
        sb.append("City: ").append(provider.city).append('\n');
        sb.append("State: ").append(provider.state).append('\n');
        sb.append("Zip: ").append(provider.zip).append('\n');
        // Get current week date time range
        final LocalDateTime[] weekRange = DateUtils.getWeekRange();
        // Query for recorded services provided
        final List<ServiceRecord> providerServiceRecords = this.serviceRecordDatabase
                .get(weekRange[0], weekRange[1])
                .filter(record -> record.providerID == provider.id)
                .sorted(Comparator.comparing(record1 -> record1.serviceDate))
                .collect(Collectors.toList());
        // Print service record information
        final int idStringLength = Integer.toString(providerServiceRecords.size()).length();
        int i = 1;
        BigDecimal totalFee = BigDecimal.ZERO;
        for (final ServiceRecord record : providerServiceRecords) {
            final Service service = this.providerServiceDirectory.get(record.serviceCode);
            final Member member = this.memberDatabase.get(record.memberID);
            final String padding = StringUtils.padLeft("", idStringLength);
            sb.append(StringUtils.padLeft(Integer.toString(i), idStringLength)).append(". ").append(service != null ? service.name : "Unknown service").append('\n');
            sb.append(padding).append("| Date: ").append(record.serviceDate.format(DATE_FORMATTER)).append('\n');
            sb.append(padding).append("| Date received: ").append(record.dateTime.format(DATE_TIME_FORMATTER)).append('\n');
            sb.append(padding).append("| Member name: ").append(member != null ? member.name : "Unknown member").append('\n');
            sb.append(padding).append("| Member number: ").append(record.memberID).append('\n');
            sb.append(padding).append("| Service code: ").append(record.serviceCode).append('\n');
            sb.append(padding).append("` Fee: $");
            if (service != null) {
                sb.append(service.fee);
                totalFee = totalFee.add(service.fee);
            } else {
                sb.append("0 (Unknown service)");
            }
            sb.append('\n');
            i++;
        }
        // Print totals
        sb.append("Total consultations provided: ").append(providerServiceRecords.size()).append('\n');
        sb.append("Total fee for the week: $").append(totalFee).append('\n');
        // Return finished provider report
        return sb.toString();
    }

    /**
     * Generates a manager's summary report for the week.
     * @return A string containing the report contents.
     */
    public String forManager() {
        final StringBuilder sb = new StringBuilder();
        // TODO
        return sb.toString();
    }

}
