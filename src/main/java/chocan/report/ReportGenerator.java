package chocan.report;

import chocan.service.IProviderServiceDirectory;
import chocan.service.IServiceRecordDatabase;
import chocan.service.Service;
import chocan.service.ServiceRecord;
import chocan.users.*;
import chocan.utils.DateUtils;
import chocan.utils.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class ReportGenerator {

    private static final String DATE_FORMAT = "MM-dd-uuuu";

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
        sb.append("ID: ").append(member.id).append('\n');
        sb.append("Street Address: ").append(member.address).append('\n');
        sb.append("City: ").append(member.city).append('\n');
        sb.append("State: ").append(member.state).append('\n');
        sb.append("Zip: ").append(member.zip).append('\n');
        // Get current week date time range
        final LocalDateTime[] weekRange = DateUtils.getWeekRange();
        // Query for recorded services provided
        final List<ServiceRecord> memberServiceRecords = this.serviceRecordDatabase
                .get(weekRange[0], weekRange[1])
                .filter(record -> record.memberID == member.id)
                .collect(Collectors.toList());
        // Print service record information
        final int idStringLength = Integer.toString(memberServiceRecords.size()).length();
        int i = 1;
        for (final ServiceRecord record : memberServiceRecords) {
            final Service service = this.providerServiceDirectory.get(record.serviceCode);
            final Provider provider = this.providerDatabase.get(record.providerID);
            sb.append(StringUtils.padLeft(Integer.toString(i), idStringLength)).append(". ").append(service != null ? service.name : "Unknown service").append('\n');
            sb.append(StringUtils.padLeft("", idStringLength)).append("| by ").append(provider != null ? provider.name : "Unknown provider").append('\n');
            sb.append(StringUtils.padLeft("", idStringLength)).append("` on ").append(record.serviceDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            i++;
        }
        // Return finished member report
        return sb.toString();
    }

    /**
     *
     * @param provider
     * @return
     */
    public String forProvider(final Provider provider) {
        final StringBuilder sb = new StringBuilder();
        // TODO
        return sb.toString();
    }

    /**
     *
     * @param manager
     * @return
     */
    public String forManager(final Manager manager) {
        final StringBuilder sb = new StringBuilder();
        // TODO
        return sb.toString();
    }

}
