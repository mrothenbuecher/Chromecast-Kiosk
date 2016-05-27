
html.html('lang':"de"){
	head {
		meta('http-equiv':"content-type", content:"text/html; charset=UTF-8")
		meta(charset:"UTF-8")
		title('Kiosk System')
		meta( name:"generator", content:"Bootply")
		meta( name:"viewport", content:"width=device-width, initial-scale=1, maximum-scale=1")
		link( href:"assets/css/bootstrap.min.css", rel:"stylesheet")
		link( href:"assets/css/toastr.min.css", rel:"stylesheet")
		link( href:"assets/css/styles.css", rel:"stylesheet")
		script(src:"//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js")
	}
	body{
		div( class:"navbar navbar-default navbar-static-top"){
			div( class:"container"){
				div( class:"navbar-header"){
					button( type:"button", class:"navbar-toggle", 'data-toggle':"collapse", 'data-target':".navbar-collapse"){
						span( class:"icon-bar")
						span( class:"icon-bar")
						span( class:"icon-bar")
					}
					a (class:"navbar-brand", href:"#", 'Kiosk System')
				}
				div( class:"collapse navbar-collapse"){
					ul( class:"nav navbar-nav"){
						li(class:"active"){
							a(href:"#overview", 'Overview', id:"mainbutton")
						}
						li {
							a(href:"#start", 'start cast', id:"senderbutton")
						}
						li {
							a(href:"#help", 'help', id:"helpbutton")
						}
					}
				}
			}
		}

		include('/WEB-INF/main.groovy')
		include('/WEB-INF/sender.groovy')
		include('/WEB-INF/help.groovy')

		script(src:"assets/js/bootstrap.min.js")
		script(src:"assets/js/spin.min.js")
		script(src:"assets/js/toastr.min.js")
		script(src:"assets/js/main.js")
	}
}