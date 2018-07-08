<!DOCTYPE html>
<html>

<head>
  <meta charset='utf-8' />
  <link href='css/fullcalendar.css' rel='stylesheet' />
  <link href='css/fullcalendar.print.min.css' rel='stylesheet' media='print' />
  <link href='css/ticketmodel.css' rel='stylesheet'>
   <link rel="stylesheet" href="css/model.css" type="text/css">
  <link href="css/bootstrap-datepicker.css" rel="stylesheet" type="text/css" />
  <script src='js/calendar.js'></script>
</head>
<style>
    body {
      margin: 40px 10px;
      padding: 0;
      font-family: "Lucida Grande", Helvetica, Arial, Verdana, sans-serif;
      font-size: 14px;
    }

    #ticket-dialog {
      width: 90%;
      height: 90%;
    }

    #ticket-content {
      height: 100%;
    }
    
    #selected_assignee,#selected_severity{
    width: 100%;
    height: 35px;
    border: 0;
    background: #f1f1f1;
    }
    
    #selected_assignee:focus,#selected_severity:focus{
    outline:none;
    }
    
  </style>
<body>


  <!-- Button trigger modal -->
  <button type="button" class="btn create-event-btn btn-sm" data-toggle="modal" data-target="#eventModel">
    <span class="glyphicon glyphicon-plus"></span>
  </button>

  <!-- Event Modal -->
  <div class="modal fade" id="eventModel" tabindex="-1" role="dialog" aria-labelledby="eventModelLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
      <div class="modal-content">
        <form class="form-horizontal">
          <div class="modal-header">
            <h3 class="modal-title" id="eventModelLabel">New Ticket</h3>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span class="close-btn" aria-hidden="true">&times;</span>
            </button>
          </div>

          <div class="modal-body">

            <div class="container">
              <div class="row">
                <div class='col-xs-6 col-md-4'>
                  <div class="form-group">
                    <label for="inputEvent" class="col-sm-4">Issue Title</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="inputEvent" placeholder="Title" autocomplete="off" required>
                    </div>
                  </div>
                </div>
              </div>


              <div class="row">
                <div class='col-xs-6 col-md-4'>
                  <div class="form-group">
                    <label for="eventDesp" class="col-sm-4">Description</label>
                    <div class="col-sm-6 col-md-8 col-xs-4">
                      <textarea class="form-control ticket-desp" rows="10" cols="60" id="eventDesp" placeholder="description" style="width: auto;"></textarea>
                    </div>
                  </div>
                </div>
              </div>


           <!--    <div class="row">
                <div class='col-xs-8 col-md-8'>
                  <div class="form-group">
                    <label for="intercomLink" class="col-xs-6 col-sm-2 col-md-2">Link*</label>
                    <div class="col-sm-10 col-md-8">
                      <input type="text" class="form-control" id="intercomLink" placeholder="intercom link" autocomplete="off" required>
                    </div>
                  </div>
                </div>
              </div> -->
              
              
              <div class="row">
                <div class='col-xs-6 col-md-4'>
                  <div class="form-group">
                     <label for="severity" class="col-xs-8 col-sm-4 col-md-4">Severity</label>
                    <div class='col-md-8' id='severity'>
						<select id="selected_severity">
							<option value="minor">Minor</option>
							<option value="major">Major</option>
							<option value="critical">Critical</option>
						</select>
                    </div>
                  </div>
                </div>
              </div>

              <div class="row">

                <div class='col-xs-6 col-md-4'>
                  <div class="form-group">
                    <label for="assignee" class="col-xs-8 col-sm-4 col-md-4">Assign To</label>
                    <div class='col-md-8' id='assignee'>
						<select id="selected_assignee">
							<option value="">--none--</option>
						</select>
                    </div>
                  </div>
                </div>

                <div class='col-xs-6 col-md-4'>
                  <div class="form-group ">
                    <label for="issueDate" class="col-xs-8 col-sm-4 col-md-4">Due Date</label>
                    <div class='col-md-6 input-group date' id='issueDate'>
                      <input type='text' class="form-control" id="issueDateInput" autocomplete="off" required/>
                      <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                      </span>
                    </div>
                  </div>
                </div>
              </div>

            </div>
          </div>

          <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="createTicket">Create Ticket</button>
            <button type="button" class="btn btn-secondary" id="closeModel" data-dismiss="modal">Cancel</button>
          </div>

        </form>
      </div>
    </div>
  </div>

  <div id='calendar'></div>

	<div id="fullCalModal" class="modal fade">
		<div id="ticket-dialog" class="modal-dialog modal-lg">
			<div id="ticket-content" class="modal-content">
				<div class="modal-header">
					<h4 id="modalTitle" class="modal-title"></h4><span>Created on: </span> <span id="startDate"></span>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span class="close-btn" aria-hidden="true">&times;</span>
					</button>
				</div>

				<div id="modalBody" class="modal-body">
					<div class="event-desp-class">
						<h5>
							<strong>Description</strong>			
						</h5>
						<hr>
						<p id="eventDescription"></p>
					</div>
					<div style="position:relative; top:40px;" class="bug-info"><h5><strong>Information</strong></h5>
					
					<div>
					<!-- <span>Assigned To:</span><p id="assignedTo"></p> -->
					<span>DueDate : </span><span id="dueDate"></span>
					
					</div>
					<div>
					<span>Severity : </span><span id="severity_type"></span>
					</div>
					<!-- 
					 <div class="comment-container col-xs-6 col-md-4">
					 <textarea class="form-control ticket-desp" rows="5" cols="30" id="comment" placeholder="comment" style="width: auto;"></textarea>
					<div class="footer">
					<button class="btn btn-primary">Save</button></div>
					</div> -->
					 
					</div>
				</div>
			</div>
		</div>
	</div>
<script src='js/fullcalendar.js'></script>

 

  <script src="js/bootstrap-datepicker.js"></script>
  
  <script src='js/home.js'></script>

 

</body>


</html>