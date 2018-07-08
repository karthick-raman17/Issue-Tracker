$(document).ready(function() {

				$(function() {
					 $("#issueDate").datepicker({
			             autoclose: true,
			             todayHighlight: true
			         });
				});

	
				$('#calendar').fullCalendar(
						{
							aspectRatio : 2,
							nowIndicator : true,

							selectable : true,
							header : {
								left : 'title',
								center : 'listMonth,month',
								right : 'prev,next today'
							},
							views : {
								listMonth : {
									buttonText : 'Bug List',
									displayEventTime: false
								}
							},
							ignoreTimezone : true,
							defaultView : 'month',
							firstDay : 0,
							editable : true,
							select : function(start, end) {
								/* 
								 var title = $("#event-title-value").val();
								
								 var title = prompt('Create Event: ');


								 if (title) {
								 var eventData;

								 eventData = {
								 title : title,
								 start : start.format(),
								 end : end.format(),

								 };

								 console.log(eventData);

								 $("#calendar").fullCalendar('renderEvent',eventData, true);
								 } */
								 
								$("#calendar").fullCalendar('unselect');

							},
							eventClick : function(event,jsEvent, view) {
								 if (event.title) {    
										 $('#modalTitle').html(event.title);
								         $('#eventDescription').html(event.description);
								         $("#startDate").html(moment(event.start).format('MMMM Do YYYY'));
								         $("#dueDate").html(moment(event.dueDate).format('MMMM Do YYYY'));
								       //  $("#assignedTo").html(event.assignedTo);
								       $("#severity_type").html(event.severity);
								         
								         $('#fullCalModal').modal();
								      return false;
								    }
								 
							},
							

							// eventDrop
							eventDrop : function(event, delta, ui, view, revertFunc, jsEvent) {

								alert(event.title + " is moved to " + event.start);

								if (!confirm("Reschedule this event?")) {
									revertFunc();
								}
							},
							
							// eventResize
							eventResize : function(event, delta, view, revertFunc, jsEvent, ui) {

								alert(event.title + " is moved to " + event.start);

								if (!confirm("Reschedule this event?")) {
									revertFunc();
								}

							},

							droppable : true,
							drop : function(date, allDay) {
								// this function is called when something is dropped retrieve the dropped element's stored Event Object
								var originalEventObject = $(this).data('eventObject');

								// we need to copy it, so that multiple events don't have a reference to the same object
								var copiedEventObject = $.extend({},originalEventObject);

								// assign it the date that was reported
								copiedEventObject.start = date;
								copiedEventObject.allDay = allDay;

								// render the event on the calendar the last `true` argument determines if the event "sticks"

								$('#calendar').fullCalendar('renderEvent',vcopiedEventObject, true);

								// is the "remove after drop" check box checked?
								if ($('#drop-remove').is(':checked')) {
									$(this).remove();
								}

							}

						});

				$.ajax({
							url : "/alltickets",
							type : "GET",
							success : function(data) {
								$("#calendar").fullCalendar('renderEvents',data, true);
							}
						});

				
				 $.getJSON("allusers", function (data) {
			         $.each(data, function (index, value) {
			             $('#selected_assignee').append('<option class="assignee-class" title="'+value.loginId+'" value="' + value.UUID + '">' + value.firstName + '</option>');
			         });
			     });
				
				 $('#selected_assignee').change(function () {
			         console.log(this.options[this.selectedIndex].value);
			      });
			});






$("#createTicket").on("click", function() {
	

	 console.log("Creating Ticket");
	 
		var title = $("#inputEvent").val();
		
	
		var createDateInput = new Date();
		var dueDateInput = new Date($("#issueDateInput").val());
		
		var dueDate = moment(dueDateInput).format();
		var createdDate = moment(createDateInput).format();
		
		var issueDescription = $("#eventDesp").val();
		var assignedTo = $("#selected_assignee").val();
		var severity = $("#selected_severity").val();
		
		if(title !="" && title != "undefined"){
		var eventData = {
			title : title,
			start : createdDate,
			description : issueDescription,
			allDay : true,
			assignedTo : assignedTo,
			dueDate : dueDate,
			severity : severity
		}
		console.log(eventData);

		$.ajax({
			url : "/createticket",
			type : "POST",
			datatype : "application/json",
			contentType : "text/plain",
			data : JSON.stringify(eventData),
			success : function(data) {
				$('#calendar').fullCalendar({	
					eventRender : function(data, element) {
						element.qtip({
							content : data.description
						});
					}
				});
				$("#calendar").fullCalendar('renderEvent', data, true);
				$("#calendar").fullCalendar('unselect');
			}
		});

		$("#inputEvent").val("");
		$("#issueDateInput").val("");
		$("#intercomLink").val("");
		$("#eventDesp").val("");
		$("#selected_assignee").val("");
		$("#selected_severity").val("minor");
		$("#closeModel").click();
		}
		
		else{
		 alert("Enter any title");
		 
		}
		
		
	
		
		
});




	