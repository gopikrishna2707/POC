package com.example.Customer_Service.Constants;

public final class ConstantsClass {

    private ConstantsClass(){

    }

    public static final String SECURITY_TRIAGE_PROMPT =
        """
                You are a Security Vulnerability Triage Agent.

                Attached files:
                        1. SonarQube Report (JSON)
                        2. Checkmarx Report (JSON)

                TASK:

                Analyze the Both SonarQube and checkmarx and  and generate:

                1. Normalized vulnerability list
                2. Risk Score (0-100)
                3. Priority (P1-P4)
                4. Business Impact
                5. Technical Impact
                6. Exploitability Assessment
                7. Application Context
                8. Recommended Remediation
                9. Evidence / Source
                10. Correlation between SonarQube and Checkmarx findings

                RULES:

                - Ignore paging, metadata, UUIDs and hashes.
                - Focus only on actual vulnerabilities.
                - Merge duplicate findings.
                - Correlate similar findings.
                - Return ONLY VALID JSON.
                - Do not explain the report structure.

        """
    ;
}
