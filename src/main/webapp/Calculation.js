function jenkins_radio_handler() {
    var value_selected = document.getElementById('jenkins_radio_yes').checked;

    if (value_selected == true) {
        document.getElementById('jenkins_job_name').style.display = 'inline';
        document.getElementById('jenkins_runs_per_month').style.display = 'none';
    }
    else {
        document.getElementById('jenkins_job_name').style.display = 'none';
        document.getElementById('jenkins_runs_per_month').style.display = 'inline';
    }
}

function resetData() {
    $(':input')
        .not(':button, :submit, :reset, :hidden, :radio')
        .val('')
        .removeAttr('checked')
        .removeAttr('selected');
}

function loadDataFromService() {
    document.getElementById('calculation_error_div').style.display = 'none';

    //Jenkins Section
    if (document.getElementById('jenkins_runs_per_month').style.display == 'inline') {
        var runsPerPeriod = document.getElementById('runsPerPeriod').value;
    }
    else {
        var jenkinsJobName = document.getElementById('jenkinsJobName').value;
    }

    //General section
    var averageTimeToPassManualTestCase =
        document.getElementById('averageTimeToPassManualTestCase').value;
    var totalNumberOfManualTestCases =
        document.getElementById('totalNumberOfManualTestCases').value;
    var manualCostsPerHour = document.getElementById('manualCostsPerHour').value;
    var autoCostsPerHour = document.getElementById('autoCostsPerHour').value;

    //Jira section
    var jiraUrl = document.getElementById('jiraUrl').value;
    var newTestCaseTicketsFilter = document.getElementById('newTestCaseTicketsFilter').value;
    var maintenanceTicketsFilter = document.getElementById('maintenanceTicketsFilter').value;
    var releaseSupportTicketsFilter = document.getElementById('releaseSupportTicketsFilter').value;
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;
    var maxResults = document.getElementById('maxResults').value;

    var jsonObj = new Object();
    jsonObj.jiraUrl = jiraUrl;
    jsonObj.newTestCaseTicketsFilter = newTestCaseTicketsFilter;
    jsonObj.maintenanceTicketsFilter = maintenanceTicketsFilter;
    jsonObj.releaseSupportTicketsFilter = releaseSupportTicketsFilter;
    jsonObj.username = username;
    jsonObj.password = password;
    jsonObj.maxResults = maxResults;

    jsonObj.averageTimeToPassManualTestCase = averageTimeToPassManualTestCase;
    jsonObj.totalNumberOfManualTestCases = totalNumberOfManualTestCases;
    jsonObj.manualCostsPerHour = manualCostsPerHour;
    jsonObj.autoCostsPerHour = autoCostsPerHour;

    jsonObj.runsPerPeriod = runsPerPeriod;
    jsonObj.jenkinsJobName = jenkinsJobName;

    var jsonString = JSON.stringify(jsonObj);

    $.ajax({
        url: "http://localhost:8082/api/calculateRoi",
        type: "POST",
        data: jsonString,
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            $('.hidden_results_content').css('display', 'inherit');

            roi_table(data, 'table_roi_div');

            var months = new Array();
            var roiDataSet = new Array();
            var costPerMonthWithAutomation = new Array();
            var costPerMonthWithoutAutomation = new Array();
            var cumulativeCostPerMonthWithAutomation = new Array();
            var cumulativeCostPerMonthWithoutAutomation = new Array();
            var totalAutoCostPerMonth = new Array();
            var totalCumulativeAutoCostPerMonth = new Array();

            $.each(data, function (key, value) {
                $.each(value, function (k, v) {
                    if (k == "month") {
                        months.push(v)
                    }
                    if (k == "pureSavingsPercent") {
                        roiDataSet.push(v)
                    }
                    if (k == "costPerMonthWithAutomation") {
                        costPerMonthWithAutomation.push(v)
                    }
                    if (k == "costPerMonthWithoutAutomation") {
                        costPerMonthWithoutAutomation.push(v)
                    }
                    if (k == "cumulativeCostPerMonthWithAutomation") {
                        cumulativeCostPerMonthWithAutomation.push(v)
                    }
                    if (k == "cumulativeCostPerMonthWithoutAutomation") {
                        cumulativeCostPerMonthWithoutAutomation.push(v)
                    }
                    if (k == "totalAutoCostPerMonth") {
                        totalAutoCostPerMonth.push(v)
                    }
                    if (k == "totalCumulativeAutoCostPerMonth") {
                        totalCumulativeAutoCostPerMonth.push(v)
                    }
                });
            });

            chart(months, roiDataSet, "roi_chart", "Work of manual engineers", "Automation team ROI");

            pay_off_table(data, 'table_payoff_div');

            chartTwoLines(months, costPerMonthWithAutomation, costPerMonthWithoutAutomation,
                "Costs per month with automation (includes time of auto team for test creation, maintenance and release support)",
                "Costs per month without automation",
                "payoff_chart_per_month", "Pure automation investments pay off per month");
            chartTwoLines(months, cumulativeCostPerMonthWithAutomation, cumulativeCostPerMonthWithoutAutomation,
                "Cumulative costs per month with automation (includes time of auto team for test creation, maintenance and release support)",
                "Cumulative costs per month without automation",
                "cumulative_payoff_chart_per_month", "Cumulative pure automation investments pay off per month");

            chartTwoLines(months, totalAutoCostPerMonth, costPerMonthWithoutAutomation,
                "Costs per month with automation (includes full time of auto team)",
                "Costs per month without automation",
                "total_payoff_chart_per_month", "Full automation investments pay off per month");
            chartTwoLines(months, totalCumulativeAutoCostPerMonth, cumulativeCostPerMonthWithoutAutomation,
                "Cumulative costs per month with automation (includes full time of auto team)",
                "Cumulative costs per month without automation",
                "total_cumulative_payoff_chart_per_month", "Cumulative full automation investments pay off per month");

            get_final_roi(data);

        },
        error: function (data) {
            var errorMessage = "";
            $.each(data, function (key, val) {
                if (key == "responseJSON") {
                    $.each(val, function (k, v) {
                        if (k == "error") {
                            errorMessage = errorMessage + v + ": ";
                        }
                        if (k == "message") {
                            errorMessage = errorMessage + v;
                        }
                    });
                }
            });
            document.getElementById('calculation_error_div').innerHTML = errorMessage;
            document.getElementById('calculation_error_div').style.display = 'inherit';
        }
    });
}

function chart(months, dataSet, element_id, label, title) {
    var ctx = document.getElementById(element_id);
    var data = {
        labels: months,
        datasets: [{
            label: label,
            fill: false,
            lineTension: 0.1,
            backgroundColor: "#228B22",
            borderColor: "#228B22",
            borderCapStyle: 'butt',
            borderDash: [],
            borderDashOffset: 0.0,
            borderJoinStyle: 'miter',
            pointBorderColor: "#228B22",
            pointBackgroundColor: "#fff",
            pointBorderWidth: 1,
            pointHoverRadius: 5,
            pointHoverBackgroundColor: "#228B22",
            pointHoverBorderColor: "#228B22",
            pointHoverBorderWidth: 2,
            pointRadius: 1,
            pointHitRadius: 10,
            data: dataSet,
            spanGaps: false
        }]
    };

    new Chart(ctx, {
        type: 'line',
        data: data,
        options: {
            title: {
                display: true,
                text: title,
                fontFamily: 'Quattrocento Sans',
                fontSize: 18
            },
            legend: {
                display: true,
                position: 'bottom',
                labels: {
                    label: label,
                    fontFamily: 'Quattrocento Sans',
                    fontSize: 16,
                    padding: 0

                }
            },
            scales: {
                yAxes: [{
                    scaleLabel: {
                        display: true,
                        labelString: 'Manual Engineers',
                        fontFamily: 'Quattrocento Sans',
                        fontSize: 16
                    },
                    ticks: {
                        fontFamily: 'Quattrocento Sans',
                        fontSize: 14
                    }
                }],
                xAxes: [{
                    ticks: {
                        fontFamily: 'Quattrocento Sans',
                        fontSize: 14
                    }
                }]
            }
        }

    });
}

function chartTwoLines(months, firstData, secondData, firstDataLabel, secondDataLabel, element_id, title) {

    var ctx = document.getElementById(element_id);
    var data = {
        labels: months,
        datasets: [{
            label: firstDataLabel,
            fill: false,
            lineTension: 0.1,
            backgroundColor: "#228B22",
            borderColor: "#228B22",
            borderCapStyle: 'butt',
            borderDash: [],
            borderDashOffset: 0.0,
            borderJoinStyle: 'miter',
            pointBorderColor: "#228B22",
            pointBackgroundColor: "#fff",
            pointBorderWidth: 1,
            pointHoverRadius: 5,
            pointHoverBackgroundColor: "#228B22",
            pointHoverBorderColor: "#228B22",
            pointHoverBorderWidth: 2,
            pointRadius: 1,
            pointHitRadius: 10,
            data: firstData,
            spanGaps: false,
        },
            {
                label: secondDataLabel,
                fill: false,
                lineTension: 0.1,
                backgroundColor: "#B22222",
                borderColor: "#B22222",
                borderCapStyle: 'butt',
                borderDash: [],
                borderDashOffset: 0.0,
                borderJoinStyle: 'miter',
                pointBorderColor: "#B22222",
                pointBackgroundColor: "#fff",
                pointBorderWidth: 1,
                pointHoverRadius: 5,
                pointHoverBackgroundColor: "#B22222",
                pointHoverBorderColor: "#B22222",
                pointHoverBorderWidth: 2,
                pointRadius: 1,
                pointHitRadius: 10,
                data: secondData,
                spanGaps: false,
            }
        ]
    };

    new Chart(ctx, {
            type: 'line',
            data: data,
            options: {
                title: {
                    display: true,
                    text: title,
                    fontFamily: 'Quattrocento Sans',
                    fontSize: 18
                },
                legend: {
                    display: true,
                    position: 'bottom',
                    labels: {
                        fontFamily: 'Quattrocento Sans',
                        fontSize: 16,
                        padding: 10

                    }
                },
                scales: {
                    yAxes: [{
                        scaleLabel: {
                            display: true,
                            labelString: 'Sum in dollars',
                            fontFamily: 'Quattrocento Sans',
                            fontSize: 16
                        },
                        ticks: {
                            fontFamily: 'Quattrocento Sans',
                            fontSize: 14
                        }
                    }],
                    xAxes: [{
                        ticks: {
                            fontFamily: 'Quattrocento Sans',
                            fontSize: 14
                        }
                    }]
                }
            }
        }
    )
    ;

}
function roi_table(data, element_id) {
    var caption = "<table class='table_data'> <caption>ROI Per Month</caption>" +
        "<tr>" +
        "<th>Month</th>" +
        "<th>Cumulative Number of Test Cases Per Month</th>" +
        "<th>Maintenance Hours Per Month</th>" +
        "<th>Maintenance Percentage</th>" +
        "<th>Saving Hours Per Month</th>" +
        "<th>Pure Savings Hours Per Month</th>" +
        "<th>Manual Engineers Savings</th>" +
        "<th>Saving Difference</th>" +
        "</tr>";
    var columns = new Array("month", "cumulativeNumberOfTestCasesPerMonth",
        "maintenancePerMonth", "maintenancePercentage",  "savingsPerMonth",
        "pureSavingsHours", "pureSavingsPercent", "savingDifference");

    populate_table(data, caption, columns, element_id);
}

function pay_off_table(data, element_id) {
    var caption = "<table class='table_data'> <caption>Automation pay off</caption>" +
        "<tr>" +
        "<th>Month</th>" +
        "<th>Cumulative Number of Test Cases Per Month</th>" +
        "<th>Pure Automation Team Hours</th>" +
        "<th>Manual Hours With Automation</th>" +
        "<th>Manual Hours Without Automation</th>" +
        "<th>Costs With Automation</th>" +
        "<th>Costs Without Automation</th>" +
        "<th>Cumulative Costs With Automation</th>" +
        "<th>Cumulative Costs Without Automation</th>" +
        "<th>Pure Auto vs Manual Costs</th>" +
        "<th>Total Auto Costs</th>" +
        "<th>Total Cumulative Auto Costs</th>" +
        "<th>Full Auto vs Manual Costs</th>" +
        "</tr>";
    var columns = new Array("month", "cumulativeNumberOfTestCasesPerMonth",
        "autoHoursPerMonth", "manualHoursPerMonthWithAutomation", "manualHourPerMonthWithoutAutomation",
        "costPerMonthWithAutomation", "costPerMonthWithoutAutomation",
        "cumulativeCostPerMonthWithAutomation", "cumulativeCostPerMonthWithoutAutomation",
        "priceDifferencePureAutoHours",
        "totalAutoCostPerMonth", "totalCumulativeAutoCostPerMonth",
        "priceDifferenceFullAutoHours");

    populate_table(data, caption, columns, element_id);
}

function populate_table(data, caption, columns, element_id) {

    var valueSelected = caption;

    $.each(data, function (key, value) {
        valueSelected += "<tr>";
        $.each(columns, function (index, val) {
            $.each(value, function (k, v) {
                if (val == k) {
                    var highlight = k == "pureSavingsPercent" || k == "priceDifferencePureAutoHours" || k == "priceDifferenceFullAutoHours";
                    if (highlight && parseFloat(v) >= 0) {
                        valueSelected += "<td class='green'>" + v + "</td>"
                    } else if (highlight && parseFloat(v) < 0) {
                        valueSelected += "<td class='red'>" + v + "</td>"
                    } else {
                        valueSelected += "<td>" + v + "</td>"
                    }
                }
            });
        });
        valueSelected += "</tr>";
    });
    valueSelected += "</table>";
    document.getElementById(element_id).innerHTML = valueSelected;
}

function get_final_roi(data) {

    var lastRoi = 0;
    var lastSavings = 0;
    var pureAutoSavings = 0;
    var fullAutoSavings = 0;
    $.each(data, function (key, value) {
        $.each(value, function (k, v) {
            if (k == "pureSavingsPercent") {
                lastRoi = v;
            }
            if (k == "pureSavingsHours") {
                lastSavings = v;
            }
            if (k == "priceDifferencePureAutoHours") {
                pureAutoSavings = v;
            }
            if (k == "priceDifferenceFullAutoHours") {
                fullAutoSavings = v;
            }
        });
    });

    var final_results_green = 'final_results_green';
    var final_results_red = 'final_results_red';

    var lastRoiClass = final_results_green;
    var lastSavingsClass = final_results_green;
    var pureAutoSavingsClass = final_results_green;
    var fullAutoSavingsClass = final_results_green;

    if (parseFloat(lastRoi) < 0) {
        lastRoiClass = final_results_red;
    }
    if (parseFloat(lastSavings) < 0) {
        lastSavingsClass = final_results_red;
    }
    if (parseFloat(pureAutoSavings) < 0) {
        pureAutoSavingsClass = final_results_red;
    }
    if (parseFloat(fullAutoSavings) < 0) {
        fullAutoSavingsClass = final_results_red;
    }

    document.getElementById('final_savings').innerHTML = "<span class='final_savings_span final_result'>" +
        "Saved hours of manual team, hours:</span><span class=" + lastSavingsClass + ">" + lastSavings + "</span>";

    document.getElementById('final_roi').innerHTML = "<span class='final_roi_span final_result'>" +
        "Saved manual engineers work, number of engineers:</span><span class=" + lastRoiClass + ">" + lastRoi + "</span>";

    document.getElementById('final_pure_auto_payoff').innerHTML = "<span class='final_pure_auto_payoff_span final_result'>" +
        "Saved pure costs (include time for test case creation, maintenance and release support), dollars:" +
        "</span><span class=" + pureAutoSavingsClass + ">" + pureAutoSavings + "</span>";

    document.getElementById('final_full_auto_payoff').innerHTML = "<span class='final_full_auto_payoff_span final_result'>" +
        "Saved full costs (include full time of auto team), dollars:" +
        "</span><span class=" + fullAutoSavingsClass + ">" + fullAutoSavings + "</span>";

}

function show_help() {
    document.getElementById('help_text').style.display = 'block';
    document.getElementById('help_background').style.display = 'block';
}

function hide_help() {
    document.getElementById('help_text').style.display = 'none';
    document.getElementById('help_background').style.display = 'none';
}

$(document).ready(function () {
    $('#roi-form').validate({
        rules: {
            jiraUrl: {
                required: true
            },
            newTestCaseTicketsFilter: {
                required: true
            },
            maintenanceTicketsFilter: {
                required: true
            },
            releaseSupportTicketsFilter: {
                required: true
            },
            maxResults: {
                required: true,
                number: true,
                number: true
            },
            username: {
                required: true
            },
            password: {
                required: true
            },
            averageTimeToPassManualTestCase: {
                required: true,
                number: true
            },
            totalNumberOfManualTestCases: {
                required: true,
                number: true
            },
            autoCostsPerHour: {
                required: true,
                number: true
            },
            manualCostsPerHour: {
                required: true,
                number: true
            },

            runsPerPeriod: {
                required: "#runsPerPeriod:visible",
                number: true
            },
            jenkinsJobName: {
                required: "#jenkinsJobName:visible"
            },
        },
        submitHandler: function (form) {
            loadDataFromService();
            return false;
        },
        errorPlacement: function (error, element) {
            var lastError = $(element).data('lastError'),
                newError = $(error).text();
            $(element).data('lastError', newError);
            if (newError !== '' && newError !== lastError) {
                $(element).tooltip({
                    placement: "top",
                    trigger: "manual"
                }).attr('title', newError).tooltip('fixTitle').tooltip('show');
            }
        },
        success: function (label, element) {
            $(element).tooltip('hide');
        }
    });

});



