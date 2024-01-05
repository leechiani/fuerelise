// 載入
function init() {
	$.ajax({
		url: "/planorddto/finding", // 資料請求的網址
		type: "GET", // GET | POST | PUT | DELETE | PATCH
		// data: { user_id: user_id }, // 將物件資料(不用雙引號) 傳送到指定的 url
		dataType: "json", // 預期會接收到回傳資料的格式： json | xml | html
		success: function(data) {
			// console.log(data);
			let list_html = "";
			$.each(data, function(index, item) {
				list_html += `
                <tr data-planordid="${item.planOrdID}">
                    <td>${item.planOrdID}</td>
                    <td>${item.memName}</td>
                    <td>${item.planName}</td>
                    <td>${item.planStart}</td>
                    <td>${item.planEnd}</td>
                    <td>${item.total}</td>
                    <td>${item.planStatus}</td>
					<td><a href="detail?planOrdID=${item.planOrdID} "><span class="sl_btn_chakan" style="background-color: #9ac972">查看詳情</span></a></td>
                </tr>
            `;
			});
			$(".planord_list").html(list_html);
			$('#example4').DataTable();
		}
	});
}

// enter送出
$("input#planName, input#liter, input#planPrice, input#planPricePerCase").on("keyup", function(e) {
	if (e.which == 13) {
		//enter也等於按按鍵
		$("button#task_add, button#task_update").click();
	}
});


// ====更新====
$("button#task_update").on("click", function() {
	let planOrdID = $("input#planOrdID").val().trim();
	let memName = $("input#memName").val().trim();
	let planName = $("input#planName").val().trim();
	let pickupTime = $("select#pickupTime").val(); //timeID
	let weekDay = [];
	$.each($("[name='day']:checked"), function() {
		weekDay.push($(this).val());
	}); // String[] weekDay
	let pickupWay = $("select#pickupWay").val(); //wayID
	let total = $("input#total").val().trim(); //total
	let planPeriod = $("input#planPeriod").val().trim(); //planPeriod
	let planStart = $("input[name='planStart']").val(); //planStart
	let planEnd = $("input#planEnd]").val(); //planEnd
	let cityCode = $("select#cityCode").val(); //cityCode
	let floor = $("input#floor").val().trim(); //floor
	let pickupStop = $("input#pickupStop").val().trim(); //pickupstop
	let contact = $("input#contact").val().trim(); //contact
	let contactTel = $("input#contactTel").val().trim(); //contactTel
	let planOrdDate = $("input#planOrdDate").val().trim(); //planOrdDate 
	let amendLog = $("input#amendLog").val().trim(); //amendLog 
	let planStatusID = $("input#planStatusID").val().trim(); //planStatusID 
	if (planName !== "" && liter !== "" && planPrice !== "" && planPricePerCase !== "") {

		let update_data = {
			"planID": $("input#planID").val(),
			"planName": planName,
			"liter": liter,
			"planPrice": planPrice,
			"planPricePerCase": planPricePerCase,
			"times": times,

		}
		$.ajax({
			url: "/planord/updating",           // 資料請求的網址
			type: "PUT",                  // GET | POST | PUT | DELETE | PATCH
			// data: { "wayID": wayID, "wayName": wayName },                // 將物件資料(不用雙引號) 傳送到指定的 url
			contentType: "application/json",
			data: JSON.stringify(update_data),
			dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
			beforeSend: function() {       // 在 request 發送之前執行
			},

			success: function(data) {//第一層子元素為li標籤
				alert('更改成功！');
				window.location.href = '/planord/';
			},

			complete: function() {
			}
		});
	} else {
		alert('請填寫所有欄位');
	}
});

//update頁面載入
//function planName_selector() {
//	$.ajax({
//		url: "/planmall/planname", // 資料請求的網址
//		type: "GET", // GET | POST | PUT | DELETE | PATCH
//		// data: { user_id: user_id }, // 將物件資料(不用雙引號) 傳送到指定的 url
//		dataType: "json", // 預期會接收到回傳資料的格式： json | xml | html
//		success: function(data) {
//			// data = {planName, planPrice, liter}
//			// console.log(data[0][1]);
//			let list_html = "";
//			$.each(data, function(index, item) {
//				list_html += `
//                <option value="${item.planName}" data-price="${item.planPrice}">${item.planName}(${item.liter}公升)</option>
//            `;
//			});
//			$("select[name='planName']").html(list_html);
//		}
//	});
//}

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
			$("option#selectedTime").after(list_html);
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
			$("option#selectedWay").after(list_html);
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
			$("option#selectedCity").after(list_html);
		}
	});
}
