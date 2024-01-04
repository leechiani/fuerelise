// 載入

function planName_selector() {
	$.ajax({
		url: "/planmall/planname", // 資料請求的網址
		type: "GET", // GET | POST | PUT | DELETE | PATCH
		// data: { user_id: user_id }, // 將物件資料(不用雙引號) 傳送到指定的 url
		dataType: "json", // 預期會接收到回傳資料的格式： json | xml | html
		success: function(data) {
			// data = {planName, planPrice, liter}
			// console.log(data[0][1]);
			let list_html = "";
			$.each(data, function(index, item) {
				list_html += `
                <option value="${item.planName}" data-price="${item.planPrice}">${item.planName}(${item.liter}公升)</option>
            `;
			});
			$("select[name='planName']").html(list_html);
			$("select[name='planName'] option:first").prop("selected", true);
		},
		complete: function() {
			let planName = $("select[name='planName']").val(); //String planName
			let times = $("#times").val(); //String times
			let data = {
				"planName": planName,
				"times": times
			}
			$.ajax({
				url: "/planorddto/price", // 資料請求的網址
				type: "POST", // GET | POST | PUT | DELETE | PATCH
				contentType: "application/json",
				data: JSON.stringify(data),
				dataType: "json",
				success: function(data) {
					let periodLength = $("select#period option:selected").data('month');
					$('#total').val(data  * periodLength);
				}
			});
		}
	});
}

function pickupTime_selector() {
	$.ajax({
		url: "/planmall/timerange", // 資料請求的網址
		type: "GET", // GET | POST | PUT | DELETE | PATCH
		// data: { user_id: user_id }, // 將物件資料(不用雙引號) 傳送到指定的 url
		dataType: "json", // 預期會接收到回傳資料的格式： json | xml | html
		success: function(data) {
			let list_html = "";
			$.each(data, function(index, item) {
				list_html += `
                <option value="${item.timeID}">${item.timeRange}</option>
            `;
			});
			$("select#pickupTime").html(list_html);
		}
	});
}

function period_selector() {
	$.ajax({
		url: "/planmall/planperiod", // 資料請求的網址
		type: "GET", // GET | POST | PUT | DELETE | PATCH
		// data: { user_id: user_id }, // 將物件資料(不用雙引號) 傳送到指定的 url
		dataType: "json", // 預期會接收到回傳資料的格式： json | xml | html
		success: function(data) {
			let list_html = "";

			$.each(data, function(index, item) {
				let length = 28 * item.planPeriod - 1;
				list_html += `
                <option data-month="${item.planPeriod}" data-length="${length}" value="${item.periodID}">${item.planPeriod}個月</option>
            `;
			});
			$("select#period").html(list_html);
		}
	});
}

function way_selector() {
	$.ajax({
		url: "/planmall/wayname", // 資料請求的網址
		type: "GET", // GET | POST | PUT | DELETE | PATCH
		// data: { user_id: user_id }, // 將物件資料(不用雙引號) 傳送到指定的 url
		dataType: "json", // 預期會接收到回傳資料的格式： json | xml | html
		success: function(data) {
			let list_html = "";

			$.each(data, function(index, item) {
				list_html += `
                <option value="${item.wayID}">${item.wayName}</option>
            `;
			});
			$("select#pickupWay").html(list_html);
		}
	});
}

function city_selector() {
	$.ajax({
		url: "/planmall/citycode", // 資料請求的網址
		type: "GET", // GET | POST | PUT | DELETE | PATCH
		// data: { user_id: user_id }, // 將物件資料(不用雙引號) 傳送到指定的 url
		dataType: "json", // 預期會接收到回傳資料的格式： json | xml | html
		success: function(data) {
			let list_html = "";

			$.each(data, function(index, item) {
				list_html += `
                <option value="${item.cityCode}">${item.cityCode}${item.cityName}</option>
            `;
			});
			$("select#cityCode").html(list_html);
		}
	});
}

function total_selector() {
	let planName = $("select[name='planName']").val(); //String planName
	let times = $("#times").val(); //String times
	let data = {
		"planName": planName,
		"times": times
	}
	$.ajax({
		url: "/planorddto/price", // 資料請求的網址
		type: "POST", // GET | POST | PUT | DELETE | PATCH
		contentType: "application/json",
		data: JSON.stringify(data),
		dataType: "json",
		success: function(data) {
			let periodLength = $("select#period option:selected").data('month');
			$('#total').val(data * periodLength);
		}
	});
}

//上架新方案
function new_plan() {
	$.ajax({
		url: "/planmall/planname", // 資料請求的網址
		type: "GET", // GET | POST | PUT | DELETE | PATCH
		// data: { user_id: user_id }, // 將物件資料(不用雙引號) 傳送到指定的 url
		dataType: "json", // 預期會接收到回傳資料的格式： json | xml | html
		success: function(data) {
			let list_html = "";
			for (let i = 0; i < data.length; i++) {
				list_html = `
                            <div class="col">
                                <div class="p-3 border bg-light" style="text-align: center;">
                                    <p class="sl_plan_name">${data[i].planName}</p>
                                    <img src="/images/garbage.png" alt="" width="90%">
                                    <p>介紹還沒想</p>
                                </div>
                            </div>
                            `;
				$("#paste_here").append(list_html);
			}
		}
	})
};

//驗證是否還有訂單&出現優惠碼欄位
$(document).on("click", "button#show_sale", function() {
	var that = $(this);
	let contact = $("input#contact").val().trim(); //contact
	let contactTel = $("input#contactTel").val().trim(); //contactTel
	let floor = $("input#floor").val().trim(); //floor
	let pickupStop = $("input#pickupStop").val().trim(); //pickupstop
	let total = $("#total").val();
	let planStart = $("input[name='planStart']").val(); //planStart
	let times = $("select#times").val();
	let weekdayChoice = $("#sl_input .checkbox-input:checked").length;
	if (contact !== "" && contactTel !== "" && floor !== "" && pickupStop !== "") {
		if (contactTel.length == 10) {
			if (times == weekdayChoice) {

				$.ajax({
					url: "/planmall/checkenddate", // 資料請求的網址
					type: "POST", // GET | POST | PUT | DELETE | PATCH
					// data: { "planStart": planStart }, // 將物件資料(不用雙引號) 傳送到指定的 url
					data: JSON.stringify({ "planStart": planStart }),
					contentType: "application/json",
					dataType: "json", // 預期會接收到回傳資料的格式： json | xml | html
					success: function(data) {
						if (data) {
							$(".first select, input").prop('disabled', true); //選好的不可改

							let list_html = "";
							list_html += `
	                                <hr>
	                                輸入優惠碼
	                                <div class="input-group mb-3">
	                                    <input id="coupon" type="text" autofocus>
	                                </div>
	                                <button type="submit" id="task_discount" class="sl_btn_chakan" style="width:20%;">確定</button>
	                                <p></p>
	                                結帳金額
	                                <div class="input-group mb-3">
	                                    <span class="input-group-text">$</span>
	                                    <input id="after_discount" type="text" readonly>
	                                </div>	                
	                                <button type="button" id="task_add" class="sl_btn_chakan" style="width:50%;" onclick="submitForm()">結帳</button>                
	                                <hr>
	                            `;
							$(that).after(list_html);
							$(that).prop('disabled', true);
							$(that).css('background-color', 'lightgray');
							$(that).css('border-color', 'lightgray');
							$("#after_discount").val(total); //after_discount的值要動態變化
						}
						else {
							alert('尚有尚未到期訂單');
						}
					}
				});
			}
			else {
				alert('請選擇收取日');
			}
		} else {
			alert('請填手機號碼');
		}
	} else {
		alert('請填寫所有欄位');
	}
});

//優惠碼enter送出、清空優惠碼提醒字
$(document).on("keyup", "#coupon", function(e) {
	if (e.which == 13) {
		//enter也等於按按鍵
		$("button#task_discount").click();
	}
	$("#replaceMe").text("");
});

//優惠碼驗證
$(document).on("click", "#task_discount", function() {
	let before_discount = $("#after_discount").val();
	let coupon = $("#coupon").val().trim();

	var that = $(this);

	//有輸入折扣碼
	if (coupon !== "") {

		let form_data = {
			"coupon": coupon,
			"total": before_discount
		}

		$.ajax({
			url: "/sale/coupon", // 資料請求的網址
			type: "POST", // GET | POST | PUT | DELETE | PATCH
			// data: form_data, // 將物件資料(不用雙引號) 傳送到指定的 url
			contentType: "application/json",
			data: JSON.stringify(form_data),
			dataType: "text", // 預期會接收到回傳資料的格式： json | xml | html
			success: function(item) {
				let replacement = `<p id="replaceMe">${item}</p>`;

				if (item == '優惠碼不存在') {
					$("#coupon").after(replacement);
				} else if (item == '未達折扣門檻') {
					$("#coupon").after(replacement);
				} else {
					let discount = parseFloat(item.slice(1));
					let after_discount = before_discount - discount; //折扣後
					$("#coupon").after(replacement);
					$("#after_discount").val(after_discount);
					$("#coupon").prop('disabled', true);
					$(that).prop('disabled', true);
					$(that).css('background-color', 'lightgray');
					$(that).css('border-color', 'lightgray');
				}

			}, error: function(xhr) {         // request 發生錯誤的話執行
				if (xhr.status === 400) {
					var errorMessage = xhr.responseText;
					alert(errorMessage);
				} else {
					alert('連線異常');
					location.reload();
				}
			}
		});
	}
});

//新增，成功後跳轉綠界
$(document).on("click", "#task_add", function() {
	var that = $(this);
	//	$(".first select, input").prop('disabled', false); //選好的不可改
	let planName = $("select[name='planName']").val(); //String planName
	let pickupTime = $("select#pickupTime").val(); //timeID
	let period = $("select#period").val(); //periodID
	let times = $("#times").val(); //String times

	let weekDay = [];
	$.each($("[name='day']:checked"), function() {
		weekDay.push($(this).val());
	}); // String[] weekDay

	let pickupWay = $("select#pickupWay").val(); //wayID
	let planStart = $("input[name='planStart']").val(); //planStart
	let planEnd = $("input[name='planEnd']").val(); //planEnd
	let contact = $("input#contact").val().trim(); //contact
	let contactTel = $("input#contactTel").val().trim(); //contactTel
	let cityCode = $("select#cityCode").val(); //cityCode
	let floor = $("input#floor").val().trim(); //floor
	let pickupStop = $("input#pickupStop").val().trim(); //pickupstop
	let afterTotal = $("input#after_discount").val(); //total after discount

	let form_data = {
		"planName": planName,
		"timeID": pickupTime,
		"periodID": period,
		"times": times,
		"weekDay": weekDay,
		"wayID": pickupWay,
		"planStart": planStart,
		"planEnd": planEnd,
		"contact": contact,
		"contactTel": contactTel,
		"cityCode": cityCode,
		"floor": floor,
		"pickupStop": pickupStop,
		"afterTotal": afterTotal
	};

	$.ajax({
		url: "/planorddto/adding", // 資料請求的網址
		type: "POST", // GET | POST | PUT | DELETE | PATCH
		// data: form_data, // 將物件資料(不用雙引號) 傳送到指定的 url
		contentType: "application/json",
		data: JSON.stringify(form_data),
		dataType: "json", // 預期會接收到回傳資料的格式： json | xml | html
		success: function(item) {
			alert("訂購成功");
			window.location.href = '/memPlanFront.html';

			//			$.ajax({
			//			    type: "POST",
			//			    url: "/ecpay/pay",
			//			    data: JSON.stringify(item),
			//			    headers: {
			//			        "Content-Type": "application/json; charset=utf-8"  // 加入 Content-Type header
			//			    },
			//			    dataType: "json",  // 預期從伺服器接收到的資料類型
			//			    success: function(response) {
			//					console.log(response);
			//					window.sessionStorage.setItem('paymentData', JSON.stringify(response));
			//				   	console.log(window.sessionStorage.getItem('paymentData'));
			////				   	window.location.href = '/ecpay/paymentForm';
			//					window.open('/ecpay/paymentForm', '_blank');
			//			    },
			//			    error: function(xhr) {
			//			        alert("發生錯誤");
			//			        location.reload();		        
			//			    }
			//			});			

		}, error: function(xhr) {         // request 發生錯誤的話執行
			if (xhr.status === 400) {
				var errorMessage = xhr.responseText;
				alert(errorMessage);
				$(".first select, input").prop('disabled', true);
				$("input#contactTel").prop('disabled', false);
				$("input#contactTel").prop('autofocus', true);
			} else if (xhr.status === 200) {
				$(".first select, input").prop('disabled', true);
				alert('請重新登入');
			}
			else {
				alert('連線異常');
				location.reload();
			}
		},
		complete: function() {

		}
	});
});

////更改付款狀態
//$(document).on("click", "#task_next", function() {
//	var paymentDataString = sessionStorage.getItem('paymentData');
//
//	var paymentData = JSON.parse(paymentDataString);
//
//	var merchantTradeNo = paymentData.formData.MerchantTradeNo;
//
//	console.log("quesr status, MerchantTradeNo: " + merchantTradeNo);
//
//	$.ajax({
//		url: "http://localhost:8080/ecpay/status",
//		type: "GET",
//		// data: form_data, // 將物件資料(不用雙引號) 傳送到指定的 url
//		data: { tradeNo: merchantTradeNo},
//		dataType: "json", // 預期會接收到回傳資料的格式： json | xml | html
//		success: function(item) {
//			if(item){
//				console.log('pay success')
//				$.ajax({
//					url: "http://localhost:8080/ecpay/updateStatus",
//					type: "POST",
//					// data: form_data, // 將物件資料(不用雙引號) 傳送到指定的 url
//					data: { tradeNo: merchantTradeNo, status: '210001'},
//					dataType: "json", // 預期會接收到回傳資料的格式： json | xml | html
//					success: function(item) {
//
//					}
//				});
//				alert('該筆訂單付款已完成, 待實做後續功能')
//			}else{
//				console.log('pay fail')
//				alert('該筆訂單付款尚未完成付款')
//			}
//		}
//	});
//
//
//
//});


// =============
/*以下要用要改<button type="button" id="task_add" class="sl_btn_chakan" style="width:50%;">繼續</button>
的id*/

//出現信用卡欄位
//$(document).on("click", "#show_credit", function() {
//	var that = $(this);
//	let list_html = "";
//	list_html += `
//    <p></p>
//    持卡人姓名
//    <input id="myName" type="text" autofocus required><br><br>
//    信用卡卡號
//    <input type=text name=pan_no1 size=4 value="" maxlength=4 onKeyUp="setBlur(this,'pan_no2');" required>-
//    <input type=text name=pan_no2 size=4 value="" maxlength=4 onKeyUp="setBlur(this,'pan_no3');" required>-
//    <input type=text name=pan_no3 size=4 value="" maxlength=4 onKeyUp="setBlur(this,'pan_no4');" required>-
//    <input type=text name=pan_no4 size=4 value="" maxlength=4 required><br><br>
//    信用卡有效年月
//    <select id="year">
//        <option value="2024">2024</option>
//        <option value="2025">2025</option>
//        <option value="2026">2026</option>
//        <option value="2027">2027</option>
//        <option value="2028">2028</option>
//        <option value="2029">2029</option>
//        <option value="2030">2030</option>
//    </select>年
//    <select id="month" >
//        <option value="1">1</option>
//        <option value="2">2</option>
//        <option value="3">3</option>
//        <option value="4">4</option>
//        <option value="5">5</option>
//        <option value="6">6</option>
//        <option value="7">7</option>
//        <option value="8">8</option>
//        <option value="9">9</option>
//        <option value="10">10</option>
//        <option value="11">11</option>
//        <option value="12">12</option>
//    </select>月
//
//    <br><br>
//    信用卡背面末三碼
//    <input id="verify" type="password" maxlength=3 required><br><br>
//    手機號碼
//    <input id="phone" type="text" maxlength=10 required>
//    <p></p>
//    <button type="submit" id="task_add" class="sl_btn_chakan" style="width:50%;">結帳</button>
//    `;
//
//	$(that).after(list_html);
//	$(that).prop('disabled', true);
//	$(that).css('background-color', 'lightgray');
//	$(that).css('border-color', 'lightgray');
//	$("#coupon").prop('disabled', true);
//	$("#task_discount").prop('disabled', true);
//	$("#task_discount").css('background-color', 'lightgray');
//	$("#task_discount").css('border-color', 'lightgray');
//	$("#myName").focus();
//
//});
//
////驗證信用卡，success call新增function(參考chatGPT)
//$(document).on("click", "#task_add", function() {
//	var that = $(this);
//	$(".first select, input").prop('disabled', false);
//	$("input#after_discount").prop('disabled', false);
//
//	let myName = $("input#myName").val();
//	let pan_no1 = $("input[name='pan_no1']").val();
//	let pan_no2 = $("input[name='pan_no2']").val();
//	let pan_no3 = $("input[name='pan_no3']").val();
//	let pan_no4 = $("input[name='pan_no4']").val();
//	let year = $("select#year").val();
//	let month = $("select#month").val();
//	let verify = $("input#verify").val(); //planStart
//	let phone = $("input#phone").val().trim(); //contact
//	let afterTotal = $("input#after_discount").val(); //total
//
//	if (myName !== "" && verify !== "" && phone !== "" && pan_no1 !== "" && pan_no2 !== "" && pan_no3 !== "" && pan_no4 !== "") {
//
//		//串金流
//
//
//	} else {
//		alert("請填寫所有欄位");
//	}
//});

// =============
