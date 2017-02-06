
html.html('lang':"en"){
	head {
		meta('http-equiv':"content-type", content:"text/html; charset=UTF-8")
		meta(charset:"UTF-8")
		title('Kiosk System')
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
							a(href:"#overview",'data-i18n':"index_overview", 'Overview', id:"mainbutton")
						}
						li {
							a(href:"#start",'data-i18n':"index_start_cast", 'start cast', id:"senderbutton")
						}

						li {
							a(href:"#cron",'data-i18n':"index_cronjobs", 'cronjobs', id:"cronbutton")
						}
					}
					ul(class:"nav navbar-nav navbar-right"){

						li( class:"dropdown"){
							a( href:"#", class:"dropdown-toggle", 'data-toggle':"dropdown", role:"button", 'aria-haspopup':"true", 'aria-expanded':"false"){
								div(class:"pull-left", 'data-i18n':"index_lang", 'lang')
								span( class:"caret")
							}
							//new File(getServletContext().getResource("/js/file.js").getFile());
							ul(class:"dropdown-menu"){
								//TODO based on lang files
								li(class:"radio"){
									label("de")
									input(type:"radio", name:"lang",value:'de')
								}
								li(class:"radio"){
									label("en")
									input(type:"radio", name:"lang",value:'en')
								}
							}
						}
					}
				}
			}
		}

		include('/WEB-INF/main.groovy')
		include('/WEB-INF/sender.groovy')
		include('/WEB-INF/cron.groovy')
		//include('/WEB-INF/help.groovy')

		script(src:"assets/js/bootstrap.min.js")
		script(src:"assets/js/spin.min.js")
		script(src:"assets/js/toastr.min.js")
		
		// history && url
		script(src:"assets/js/jquery.history.js")
		script(src:"assets/js/url.min.js")

		// i18n support
		script(src:"assets/js/jquery.i18n.js")
		script(src:"assets/js/jquery.i18n.messagestore.js")
		script(src:"assets/js/jquery.i18n.fallbacks.js")
		script(src:"assets/js/jquery.i18n.language.js")
		script(src:"assets/js/jquery.i18n.parser.js")
		script(src:"assets/js/jquery.i18n.emitter.js")
		script(src:"assets/js/jquery.i18n.emitter.bidi.js")

		// custom scripts
		script(src:"assets/js/main.js")
		script(src:"assets/js/lang.js")
	}
}