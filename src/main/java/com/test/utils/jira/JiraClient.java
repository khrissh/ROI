package com.test.utils.jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public class JiraClient {

    private static JiraRestClient jiraRestClient;

    private JiraClient() {
    }

    private static JiraRestClient createJiraClient(String jiraUrl, String username, String password) throws URISyntaxException, ExecutionException, InterruptedException {

        AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();

        URI jiraServerUri = new URI(jiraUrl);
        JiraRestClient restClient = factory.createWithBasicHttpAuthentication(jiraServerUri, username, password);

        return restClient;
    }

    public static JiraRestClient getJiraClient(String jiraUrl, String username, String password) throws InterruptedException, ExecutionException, URISyntaxException {
        if (jiraRestClient == null) {
            jiraRestClient = createJiraClient(jiraUrl, username, password);
        }
        return jiraRestClient;
    }

}
