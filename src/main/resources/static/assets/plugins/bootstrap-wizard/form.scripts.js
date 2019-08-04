"use strict"; // Start of use strict
var next_step = false;
var LANG_PROF = 0, TEST = 1, FAM = 2, REL = 3, EDU = 4, WORK = 5, PAYMENT = 6;
var tabHeads = {
	0: ['Interpretation', 'WritingLevel', 'ReadingLevel', 'SpeakingLevel', 'Language'],
	1: ['TestDate', 'TestScore', 'TestName'],
	2: ['WorkExperience', 'HightestDegree', 'DateofBirth', 'Relation', 'Name'],
	3: ['CityandCountry', 'Relation', 'RelativeName'],
	4: ['Discipline', 'Degree', 'University', 'ToDate', 'FromDate'],
	5: ['Designation', 'EmployerNameandAddress', 'ToDate', 'FromDate'],
	6: ['Remarks', 'Amount', 'Cheque/DD Number', 'Mode', 'TransactionDate', 'ID']
};
var grades = ['Fluent', 'Good', 'Compenent', 'None'];
var langs = ['English', 'French'];
var modes = ['Cash', 'Cheque', 'Demand Draft', 'Online', 'Credit Card'];
var langProfs = [];
var tests = [];
var families = [];
var relatives = [];
var edus = [];
var workexps = [];
var payments = [];
var selectedID;

function scroll_to_class(element_class, removed_height) {
	var scroll_to = $(element_class).offset().top - removed_height;
	if ($(window).scrollTop() !== scroll_to) {
		$('html, body').stop().animate({
			scrollTop: scroll_to
		}, 0);
	}
}

function bar_progress(progress_line_object, direction) {
	var number_of_steps = progress_line_object.data('number-of-steps');
	var now_value = progress_line_object.data('now-value');
	var new_value = 0;
	if (direction === 'right') {
		new_value = now_value + (100 / number_of_steps);
	} else if (direction === 'left') {
		new_value = now_value - (100 / number_of_steps);
	}
	progress_line_object.attr('style', 'width: ' + new_value + '%;').data('now-value', new_value);
}

function evalForm() {
	var errorMsg = 'You can\'t leave this Empty!';
	$('.f1 fieldset:visible').find('input, select, textarea').each(function (i, v) {
		$(v).removeClass('input-error');
		if ($(v).attr('data-required') != undefined && $(v).attr('data-mobile') === undefined && ($(v).val() === null || $(v).val().length === 0)) {
			$(v).addClass('input-error');
			if ($(v).attr('data-message') != undefined || $(v).attr('data-message') == '')
				$(v).after('<p class="sm-text sm-gutter color-red err">' + $(v).attr('data-message') + '</p>');
			else
				$(v).after('<p class="sm-text sm-gutter color-red err">' + errorMsg + '</p>');
		}
		else
			if ($(v).attr('data-mobile') != undefined && $(v).val().length != 10)
				$(v).after('<p class="sm-text sm-gutter color-red err">Not a Vaild Mobile Number!</p>');
	});
	next_step = $('.f1 fieldset:visible').find('.err').length === 0;
}

function initTableHeads(i) {
	switch (i) {
		case LANG_PROF:
			$(tabHeads[i]).each(function (i, v) {
				$('#lang-prof-tab .tab-head').prepend('<th>' + v + '</th>');
			});
			break;
		case TEST:
			$(tabHeads[i]).each(function (i, v) {
				$('#test-tab .tab-head').prepend('<th>' + v + '</th>');
			});
			break;
		case FAM:
			$(tabHeads[i]).each(function (i, v) {
				$('#family-tab .tab-head').prepend('<th>' + v + '</th>');
			});
			break;
		case REL:
			$(tabHeads[i]).each(function (i, v) {
				$('#rel-tab .tab-head').prepend('<th>' + v + '</th>');
			});
			break;
		case EDU:
			$(tabHeads[i]).each(function (i, v) {
				$('#edu-tab .tab-head').prepend('<th>' + v + '</th>');
			});
			break;
		case WORK:
			$(tabHeads[i]).each(function (i, v) {
				$('#work-tab .tab-head').prepend('<th>' + v + '</th>');
			});
			break;
		case PAYMENT:
			$(tabHeads[i]).each(function (i, v) {
				$('#payment-tab .tab-head').prepend('<th>' + v + '</th>');
			});
			break;
	}
}

function initOptions() {
	for (var i = 0; i < grades.length; i++)
		$('.grade').append('<option value="' + grades[i] + '">' + grades[i] + '</option>');
	for (var i = 0; i < langs.length; i++)
		$('.lang').append('<option value="' + langs[i] + '">' + langs[i] + '</option>');
	for (var i = 0; i < modes.length; i++)
		$('.modes').append('<option value="' + modes[i] + '">' + modes[i] + '</option>');
}

function refreshTabs(i) {
	switch (i) {
		case LANG_PROF:
			$('#lang-prof-tab .tab-body').html('');
			$(langProfs).each(function (i, o) {
				var row = '<tr><td>' + o.lang + '</td><td>' + o.speak + '</td><td>' + o.read + '</td><td>' + o.write + '</td><td>' + o.inter + '</td><td><i class="fa fa-edit edit-lang-prof" data-toggle="modal" data-target="#modal-langProf" aria-hidden="true" data-id="' + i + '"></i> <i class="fa fa-trash trash-lang-prof" aria-hidden="true" data-id="' + i + '"></i></td></tr>';
				$('#lang-prof-tab .tab-body').append(row);
			});
			break;
		case TEST:
			$('#test-tab .tab-body').html('');
			$(tests).each(function (i, o) {
				var row = '<tr><td>' + o.name + '</td><td>' + o.score + '</td><td>' + o.date + '</td><td><i class="fa fa-edit edit-test" aria-hidden="true" data-toggle="modal" data-target="#modal-test" data-id="' + i + '"></i> <i class="fa fa-trash trash-test" aria-hidden="true" data-id="' + i + '"></i></td></tr>';
				$('#test-tab .tab-body').append(row);
			});
			break;
		case FAM:
			$('#family-tab .tab-body').html('');
			$(families).each(function (i, o) {
				var row = '<tr><td>' + o.name + '</td><td>' + o.rel + '</td><td>' + o.dob + '</td><td>' + o.deg + '</td><td>' + o.exp + '</td><td><i class="fa fa-edit edit-fam" aria-hidden="true" data-toggle="modal" data-target="#modal-family" data-id="' + i + '"></i> <i class="fa fa-trash trash-fam" aria-hidden="true" data-id="' + i + '"></i></td></tr>';
				$('#family-tab .tab-body').append(row);
			});
			break;
		case REL:
			$('#rel-tab .tab-body').html('');
			$(relatives).each(function (i, o) {
				var row = '<tr><td>' + o.name + '</td><td>' + o.rel + '</td><td>' + o.cityCountry + '</td><td><i class="fa fa-edit edit-rel" data-toggle="modal" data-target="#modal-relative" aria-hidden="true" data-id="' + i + '"></i> <i class="fa fa-trash trash-rel" aria-hidden="true" data-id="' + i + '"></i></td></tr>';
				$('#rel-tab .tab-body').append(row);
			});
			break;
		case EDU:
			$('#edu-tab .tab-body').html('');
			$(edus).each(function (i, o) {
				var row = '<tr><td>' + o.fromDate + '</td><td>' + o.toDate + '</td><td>' + o.univ + '</td><td>' + o.deg + '</td><td>' + o.disc + '</td><td><i class="fa fa-edit edit-edu" data-toggle="modal" data-target="#modal-edu" aria-hidden="true" data-id="' + i + '"></i> <i class="fa fa-trash trash-edu" aria-hidden="true" data-id="' + i + '"></i></td></tr>';
				$('#edu-tab .tab-body').append(row);
			});
			break;
		case WORK:
			$('#work-tab .tab-body').html('');
			$(workexps).each(function (i, o) {
				var row = '<tr><td>' + o.fromDate + '</td><td>' + o.toDate + '</td><td>' + o.nameAddress + '</td><td>' + o.desig + '</td><td><i class="fa fa-edit edit-work" data-toggle="modal" data-target="#modal-work" aria-hidden="true" data-id="' + i + '"></i> <i class="fa fa-trash trash-work" aria-hidden="true" data-id="' + i + '"></i></td></tr>';
				$('#work-tab .tab-body').append(row);
			});
			break;
		case PAYMENT:
			$('#payment-tab .tab-body').html('');
			$(payments).each(function (i, o) {
				var row = '<tr><td>' + o.id + '</td><td>' + o.tDate + '</td><td>' + o.modes + '</td><td>' + o.no + '</td><td>' + o.amt + '</td><td>'+ o.rem + '</td></tr>';
				$('#payment-tab .tab-body').append(row);
			});
			break;
	}
}

function addRow(i) {
	switch (i) {
		case LANG_PROF:
			langProfs.push({
				'lang': $('#lang').val(),
				'speak': $('#speak').val(),
				'read': $('#read').val(),
				'write': $('#write').val(),
				'inter': $('#inter').val()
			});
			if ($('#lang-prof-submit').attr('data-update') != undefined)
				langProfs[selectedID] = langProfs.splice(langProfs.length - 1)[0];
			$('#lang-prof-submit').removeAttr('data-update');
			break;
		case TEST:
			tests.push({
				'name': $('#test-name').val(),
				'score': $('#test-score').val(),
				'date': $('#test-date').val()
			});
			if ($('#test-form-submit').attr('data-update') != undefined)
				tests[selectedID] = tests.splice(tests.length - 1)[0];
			$('#test-form-submit').removeAttr('data-update');
			break;
		case FAM:
			families.push({
				'name': $('#family-name').val(),
				'rel': $('#family-rel').val(),
				'dob': $('#family-dob').val(),
				'deg': $('#family-deg').val(),
				'exp': $('#family-work-exp').val()
			});
			if ($('#family-form-submit').attr('data-update') != undefined)
				families[selectedID] = families.splice(families.length - 1)[0];
			$('#family-form-submit').removeAttr('data-update');
			break;
		case REL:
			relatives.push({
				'name': $('#rel-name').val(),
				'rel': $('#rel-rel').val(),
				'cityCountry': $('#rel-city-country').val()
			});
			if ($('#relative-form-submit').attr('data-update') != undefined)
				relatives[selectedID] = relatives.splice(relatives.length - 1)[0];
			$('#relative-form-submit').removeAttr('data-update');
			break;
		case EDU:
			edus.push({
				'fromDate': $('#edu-frm').val(),
				'toDate': $('#edu-to').val(),
				'univ': $('#edu-univ').val(),
				'deg': $('#edu-deg').val(),
				'disc': $('#edu-disc').val()
			});
			if ($('#edu-form-submit').attr('data-update') != undefined)
				edus[selectedID] = edus.splice(edus.length - 1)[0];
			$('#edu-form-submit').removeAttr('data-update');
			break;
		case WORK:
			workexps.push({
				'fromDate': $('#work-frm').val(),
				'toDate': $('#work-to').val(),
				'nameAddress': $('#emp-name-addr').val(),
				'desig': $('#work-desig').val()
			});
			if ($('#work-form-submit').attr('data-update') != undefined)
				workexps[selectedID] = workexps.splice(workexps.length - 1)[0];
			$('#work-form-submit').removeAttr('data-update');
			break;
		case PAYMENT:
			payments.push({
				'id': $('#pay-id').val(),
				'tDate': $('#pay-date').val(),
				'modes': $('#pay-mode').val(),
				'no': $('#pay-no').val(),
				'amt': $('#pay-amt').val(),
				'rem': $('#pay-rem').val()				
			});
			break;
	}
	refreshTabs(i);
}

function resetForm(o){
	$(o).find('.err').remove();
	$(o).find('select, input, textarea').val('');
}


(function(){
	$('#lang-prof-tab tbody tr').each(function(i, v){
		var a = [];
		$(v).find('td').each(function(i, v){
			a.push($(v).html());
		});
		if (a[0] != undefined)
			langProfs.push({
				'lang': a[0],
				'speak': a[1],
				'read': a[2],
				'write': a[3],
				'inter': a[4]
			});
		refreshTabs(LANG_PROF);
	});
	$('#test-tab tbody tr').each(function(i, v){
		var a = [];
		$(v).find('td').each(function(i, v){
			a.push($(v).html());
		});
		if (a[0] != undefined)
			tests.push({
				'name': a[0],
				'score': a[1],
				'date': a[2]
			});
		refreshTabs(TEST);
	});
	$('#family-tab tbody tr').each(function(i, v){
		var a = [];
		$(v).find('td').each(function(i, v){
			a.push($(v).html());
		});
		if (a[0] != undefined)
			families.push({
				'name': a[0],
				'rel': a[1],
				'dob': a[2],
				'deg': a[3],
				'exp': a[4]
			});
		refreshTabs(FAM);
	});
	$('#rel-tab tbody tr').each(function(i, v){
		var a = [];
		$(v).find('td').each(function(i, v){
			a.push($(v).html());
		});
		if (a[0] != undefined)
			relatives.push({
				'name': a[0],
				'rel': a[1],
				'cityCountry': a[2]
			});
		refreshTabs(REL);
	});
	$('#edu-tab tbody tr').each(function(i, v){
		var a = [];
		$(v).find('td').each(function(i, v){
			a.push($(v).html());
		});
		if (a[0] != undefined)
			edus.push({
				'fromDate': a[0],
				'toDate': a[1],
				'univ': a[2],
				'deg': a[3],
				'disc': a[4]
			});
		refreshTabs(EDU);
	});
	$('#work-tab tbody tr').each(function(i, v){
		var a = [];
		$(v).find('td').each(function(i, v){
			a.push($(v).html());
		});
		if (a[0] != undefined)
			workexps.push({
				'fromDate': a[0],
				'toDate': a[1],
				'nameAddress': a[2],
				'desig': a[3]
			});
		refreshTabs(WORK);
	});
	
})();

jQuery(document).ready(function () {
	// Form

	initTableHeads(LANG_PROF);
	initTableHeads(TEST);
	initTableHeads(FAM);
	initTableHeads(REL);
	initTableHeads(EDU);
	initTableHeads(WORK);
	initTableHeads(PAYMENT);
	initOptions();
	
	$('.f1 fieldset:first').fadeIn('slow');

	$('.f1 input[type="text"], .f1 input[type="password"], .f1 textarea').on('focus', function () {
		$(this).removeClass('input-error');
	});

	// next step
	$('.f1 .btn-next').on('click', function () {
		var parent_fieldset = $(this).parents('fieldset');

		// navigation steps / progress steps
		var current_active_step = $(this).parents('.f1').find('.f1-step.active');
		var progress_line = $(this).parents('.f1').find('.f1-progress-line');
		$('.f1 fieldset').each(function (i, v) {
			if ($(v).css('display') == 'block') {
				$('.err').remove();
				evalForm();
			}
		});

		if (next_step) {
			parent_fieldset.fadeOut(400, function () {
				// change icons
				current_active_step.removeClass('active').addClass('activated').next().addClass('active');
				// progress bar
				bar_progress(progress_line, 'right');
				// show next step
				$(this).next().fadeIn();
				// scroll window to beginning of the form
				scroll_to_class($('.f1'), 20);
			});
		}

	});

	// previous step
	$('.f1 .btn-previous').on('click', function () {
		// navigation steps / progress steps
		var current_active_step = $(this).parents('.f1').find('.f1-step.active');
		var progress_line = $(this).parents('.f1').find('.f1-progress-line');

		$(this).parents('fieldset').fadeOut(400, function () {
			// change icons
			current_active_step.removeClass('active').prev().removeClass('activated').addClass('active');
			// progress bar
			bar_progress(progress_line, 'left');
			// show previous step
			$(this).prev().fadeIn();
			// scroll window to beginning of the form
			scroll_to_class($('.f1'), 20);
		});
	});

	$('#lang-prof-submit').on('click', function () {
		var errorMsg = 'You can\'t leave this Empty!';
		$('.err').remove();
		$('#lang-prof-form').find('input, select, textarea').each(function (i, v) {
			$(v).removeClass('input-error');
			if ($(v).attr('data-required') != undefined && ($(v).val() === null || $(v).val().length === 0)) {
				$(v).addClass('input-error');
				if ($(v).attr('data-message') != undefined || $(v).attr('data-message') == '')
					$(v).after('<p class="sm-text sm-gutter color-red err">' + $(v).attr('data-message') + '</p>');
				else
					$(v).after('<p class="sm-text sm-gutter color-red err">' + errorMsg + '</p>');
			}
		});
		if ($('#lang-prof-form').find('.err').length === 0) {
			addRow(LANG_PROF);
			$('#modal-langProf').modal('toggle');
		}
	});

	$('#test-form-submit').on('click', function () {
		var errorMsg = 'You can\'t leave this Empty!';
		$('.err').remove();
		$('#test-form').find('input, select, textarea').each(function (i, v) {
			$(v).removeClass('input-error');
			if ($(v).attr('data-required') != undefined && ($(v).val() === null || $(v).val().length === 0)) {
				$(v).addClass('input-error');
				if ($(v).attr('data-message') != undefined || $(v).attr('data-message') == '')
					$(v).after('<p class="sm-text sm-gutter color-red err">' + $(v).attr('data-message') + '</p>');
				else
					$(v).after('<p class="sm-text sm-gutter color-red err">' + errorMsg + '</p>');
			}
		});
		if ($('#test-form').find('.err').length === 0) {
			addRow(TEST);
			$('#modal-test').modal('toggle');
		}
	});

	$('#family-form-submit').on('click', function () {
		var errorMsg = 'You can\'t leave this Empty!';
		$('.err').remove();
		$('#family-form').find('input, select, textarea').each(function (i, v) {
			$(v).removeClass('input-error');
			if ($(v).attr('data-required') != undefined && ($(v).val() === null || $(v).val().length === 0)) {
				$(v).addClass('input-error');
				if ($(v).attr('data-message') != undefined || $(v).attr('data-message') == '')
					$(v).after('<p class="sm-text sm-gutter color-red err">' + $(v).attr('data-message') + '</p>');
				else
					$(v).after('<p class="sm-text sm-gutter color-red err">' + errorMsg + '</p>');
			}
		});
		if ($('#family-form').find('.err').length === 0) {
			addRow(FAM);
			$('#modal-family').modal('toggle');
		}
	});

	$('#relative-form-submit').on('click', function () {
		var errorMsg = 'You can\'t leave this Empty!';
		$('.err').remove();
		$('#rel-form').find('input, select, textarea').each(function (i, v) {
			$(v).removeClass('input-error');
			if ($(v).attr('data-required') != undefined && ($(v).val() === null || $(v).val().length === 0)) {
				$(v).addClass('input-error');
				if ($(v).attr('data-message') !== undefined || $(v).attr('data-message') === '')
					$(v).after('<p class="sm-text sm-gutter color-red err">' + $(v).attr('data-message') + '</p>');
				else
					$(v).after('<p class="sm-text sm-gutter color-red err">' + errorMsg + '</p>');
			}
		});
		if ($('#rel-form').find('.err').length === 0) {
			addRow(REL);
			$('#modal-relative').modal('toggle');
		}
	});
	
	$('#edu-form-submit').on('click', function () {
		var errorMsg = 'You can\'t leave this Empty!';
		$('.err').remove();
		$('#edu-form').find('input, select, textarea').each(function (i, v) {
			$(v).removeClass('input-error');
			if ($(v).attr('data-required') != undefined && ($(v).val() === null || $(v).val().length === 0)) {
				$(v).addClass('input-error');
				if ($(v).attr('data-message') !== undefined || $(v).attr('data-message') === '')
					$(v).after('<p class="sm-text sm-gutter color-red err">' + $(v).attr('data-message') + '</p>');
				else
					$(v).after('<p class="sm-text sm-gutter color-red err">' + errorMsg + '</p>');
			}
		});
		if ($('#edu-form').find('.err').length === 0) {
			addRow(EDU);
			$('#modal-edu').modal('toggle');
		}
	});
	
	$('#work-form-submit').on('click', function () {
		var errorMsg = 'You can\'t leave this Empty!';
		$('.err').remove();
		$('#work-form').find('input, select, textarea').each(function (i, v) {
			$(v).removeClass('input-error');
			if ($(v).attr('data-required') != undefined && ($(v).val() === null || $(v).val().length === 0)) {
				$(v).addClass('input-error');
				if ($(v).attr('data-message') !== undefined || $(v).attr('data-message') === '')
					$(v).after('<p class="sm-text sm-gutter color-red err">' + $(v).attr('data-message') + '</p>');
				else
					$(v).after('<p class="sm-text sm-gutter color-red err">' + errorMsg + '</p>');
			}
		});
		if ($('#work-form').find('.err').length === 0) {
			addRow(WORK);
			$('#modal-work').modal('toggle');
		}
	});
	
	$('#pay-form-submit').on('click', function () {
		var errorMsg = 'You can\'t leave this Empty!';
		$('.err').remove();
		$('#pay-form').find('input, select, textarea').each(function (i, v) {
			$(v).removeClass('input-error');
			if ($(v).attr('data-required') != undefined && ($(v).val() === null || $(v).val().length === 0)) {
				$(v).addClass('input-error');
				if ($(v).attr('data-message') !== undefined || $(v).attr('data-message') === '')
					$(v).after('<p class="sm-text sm-gutter color-red err">' + $(v).attr('data-message') + '</p>');
				else
					$(v).after('<p class="sm-text sm-gutter color-red err">' + errorMsg + '</p>');
			}
		});
		if ($('#pay-form').find('.err').length === 0) {
			addRow(PAYMENT);
			$('#pay-modal').modal('toggle');
		}
	});
	
	$(document).on('click', '.trash-lang-prof', function () {
		langProfs.splice($(this).attr('data-id'), 1);
		refreshTabs(LANG_PROF);
	});

	$(document).on('click', '.trash-test', function () {
		tests.splice($(this).attr('data-id'), 1);
		refreshTabs(TEST);
	});
	
	$(document).on('click', '.trash-fam', function () {
		families.splice($(this).attr('data-id'), 1);
		refreshTabs(FAM);
	});
	
	$(document).on('click', '.trash-rel', function () {
		relatives.splice($(this).attr('data-id'), 1);
		refreshTabs(REL);
	});
	
	$(document).on('click', '.trash-edu', function () {
		edus.splice($(this).attr('data-id'), 1);
		refreshTabs(EDU);
	});
	
	$(document).on('click', '.trash-work', function () {
		workexps.splice($(this).attr('data-id'), 1);
		refreshTabs(WORK);
	});
	
	$(document).on('click', '.edit-lang-prof', function () {
		selectedID = $(this).attr('data-id');
		$('#lang').val(langProfs[selectedID].lang);
		$('#speak').val(langProfs[selectedID].speak);
		$('#write').val(langProfs[selectedID].write);
		$('#read').val(langProfs[selectedID].read);
		$('#inter').val(langProfs[selectedID].inter);
		$('#lang-prof-submit').attr('data-update', '');
	});

	$(document).on('click', '.edit-test', function () {
		selectedID = $(this).attr('data-id');
		$('#test-name').val(tests[selectedID].name);
		$('#test-score').val(tests[selectedID].score);
		$('#test-date').val(tests[selectedID].date);
		$('#test-form-submit').attr('data-update', '');
	});
	
	$(document).on('click', '.edit-fam', function () {
		selectedID = $(this).attr('data-id');
		$('#family-name').val(families[selectedID].name);
		$('#family-rel').val(families[selectedID].rel);
		$('#family-dob').val(families[selectedID].dob);
		$('#family-deg').val(families[selectedID].deg);
		$('#family-work-exp').val(families[selectedID].exp);
		$('#family-form-submit').attr('data-update', '');
	});
	
	$(document).on('click', '.edit-rel', function () {
		selectedID = $(this).attr('data-id');
		$('#rel-name').val(relatives[selectedID].name);
		$('#rel-rel').val(relatives[selectedID].rel);
		$('#rel-city-country').val(relatives[selectedID].cityCountry);
		$('#relative-form-submit').attr('data-update', '');
	});
	
	$(document).on('click', '.edit-edu', function () {
		selectedID = $(this).attr('data-id');
		$('#edu-frm').val(edus[selectedID].fromDate);
		$('#edu-to').val(edus[selectedID].toDate);
		$('#edu-univ').val(edus[selectedID].univ);
		$('#edu-deg').val(edus[selectedID].deg);
		$('#edu-disc').val(edus[selectedID].disc);
		$('#edu-form-submit').attr('data-update', '');
	});
	
	$(document).on('click', '.edit-work', function () {
		selectedID = $(this).attr('data-id');
		$('#work-frm').val(workexps[selectedID].fromDate);
		$('#work-to').val(workexps[selectedID].toDate);
		$('#emp-name-addr').val(workexps[selectedID].nameAddress);
		$('#work-desig').val(workexps[selectedID].desig);
		$('#work-form-submit').attr('data-update', '');
	});
	
	$(document).on('click', '.notify', function () {
		var For=$(this).attr('id');
		 $("#for").val( For );
	});
	
	$('#marital-stat').on('change', function () {
		$('.tab-toggle').hide();
		if ($(this)[0].selectedIndex > 0)
			$('#relatives-details').show();
		if ($(this)[0].selectedIndex > 1)
			$('#family-details').show();
	});
	
	$('#modal-langProf, #modal-test, #modal-family, #modal-relative, #modal-edu, #modal-work, #pay-modal').on('hidden.bs.modal', function () {
		resetForm($(this));
	});

	

});