package chocan.report;

import chocan.service.IProviderServiceDirectory;
import chocan.service.IServiceRecord;
import chocan.service.IServiceRecordDatabase;
import chocan.users.IUserDatabase;
import chocan.users.MemberUser;
import chocan.users.User;

import javax.annotation.Nullable;

/**
 * An interface responsible for generating various reports and records.
 */
public interface ReportGenerator {

    /**
     * Generates a member's report for the week.
     * @param member The member to generate the report for.
     * @return A string containing the report contents.
     */
    String member(final MemberUser member,
                  final IUserDatabase<? extends User> providerDatabase,
                  final IProviderServiceDirectory providerServiceDirectory,
                  final IServiceRecordDatabase<? extends IServiceRecord> serviceRecordDatabase);

    /**
     * Generates a provider's report for the week.
     * @param provider The provider to generate the report for.
     * @return A string containing the report contents.
     */
    String provider(final User provider,
                    final IUserDatabase<? extends MemberUser> memberDatabase,
                    final IProviderServiceDirectory providerServiceDirectory,
                    final IServiceRecordDatabase<? extends IServiceRecord> serviceRecordDatabase);

    /**
     * Generates a single EFT record for the given provider.
     * If the provider didn't provide any services during the current week, then <code>null</code> will be returned.
     * @param provider The provider to generate the EFT record for.
     * @return A string containing the EFT record (fields delimited by commas), or <code>null</code> if no services provided.
     */
    @Nullable
    String eftRecord(final User provider,
                     final IUserDatabase<? extends User> providerDatabase,
                     final IProviderServiceDirectory providerServiceDirectory,
                     final IServiceRecordDatabase<? extends IServiceRecord> serviceRecordDatabase);

    /**
     * Generate EFT records for all providers that provided services in the current week.
     * @return A string of EFT records delimited by new line characters.
     */
    String eftRecords(final IUserDatabase<? extends User> providerDatabase,
                      final IProviderServiceDirectory providerServiceDirectory,
                      final IServiceRecordDatabase<? extends IServiceRecord> serviceRecordDatabase);

    /**
     * Generates a manager's summary report for the week.
     * @return A string containing the report contents.
     */
    String manager(final IUserDatabase<? extends User> providerDatabase,
                   final IProviderServiceDirectory providerServiceDirectory,
                   final IServiceRecordDatabase<? extends IServiceRecord> serviceRecordDatabase);

}
