//s_sv_globals.onListLoaded('','','','rea-live',[]);
$(function() {
	var hidden = $('.hiddenMore');
	var s4 = hidden.eq(0).parent().children(".s3");
	var aa = $('#deltaItemValue').val();
	if (aa > 13) {
		s4.css('height', 'auto');
		hidden.eq(0).css('background-image', 'url(/images/shouqi.gif)');
	}
	hidden.click(function() {
		var s3 = $(this).parent().children(".s3");
		// alert($(this).css('background-image'));
		var bg = $(this).css('background-image');
		// alert(bg.indexOf('zhankai'));

		// alert(aa);
		if (bg.indexOf('zhankai') > 0) {// 当前是关闭，点击之后展开
			s3.css('height', 'auto');
			$(this).css('background-image', 'url(/images/shouqi.gif)');
		} else {
			s3.css('height', '22px');
			$(this).css('background-image', 'url(/images/zhankai.gif)');
		}
	});

	var bar_title = $('.schooldistrict ul li');
	bar_title.eq(0).addClass('hover');
	bar_title.each(function(index) {
		$(this).mouseover(function() {
			bar_title.removeClass('hover');
			$(this).addClass('hover');
		});
	});

	var proMessage = $('.proMessage');
	var inputBox = $("#registeBox .txtbox");

	inputBox.each(function() {
		$(this).focus(function() {
			$(this).css('border', '1px solid #2188df');
			proMessage.hide();
			$(this).siblings('.proMessage').show();
		});
	});
	inputBox.each(function() {
		$(this).blur(function() {
			$(this).css('border', '1px solid #ababab');
			$(this).siblings('.proMessage').hide();
		});
	});

	var inputText = $('.inputText');
	inputText.each(function() {
		$(this).focus(function() {
			$(this).css('border', '1px solid #07a111');
		});
	});
	inputText.each(function() {
		$(this).blur(function() {
			$(this).css('border', '1px solid #ccc');
		});
	});
});
