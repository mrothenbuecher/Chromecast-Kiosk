html.div {
	div( class:"container", id:"sender", style:"display:none;"){
		div(class:"col-xs-12"){
			div( class:"page-header"){ h1('start cast') }
		}
		div(class:"col-xs-12"){
			form(method:"get",class:"form-horizontal", action:"JavaScript:connect();"){
				div( class:"form-group"){
					label(class:"col-sm-2 control-label", "Url:")
					div( class:"col-sm-10"){
						input( type:"text",class:"form-control", id:"url", placeholder:"http://www....")
					}
				}
				div( class:"form-group"){
					div( class:"col-sm-offset-2 col-sm-10"){
						button( type:"submit", class:"btn btn-default","launch")
					}
				}
			}
			div(class:"form-horizontal"){
				div( class:"form-group"){
					label(class:"col-sm-2 control-label", "Reload page after sec.")
					div( class:"col-sm-10"){
						input( type:"number",class:"form-control", id:"refresh", min:"0", value:"600")
					}
				}
			}
			button( id:"kill", style:"display: none",disabled:"disabled","Stop casting")
			p( id:"post-note", style:"display: none",
			"""If the page does not load please be sure HTTP header X-Frame-Options
			  allows the page to be loaded inside a frame not on the same origin.""")
		}
	}
	script(src:"assets/js/sender.js")
}