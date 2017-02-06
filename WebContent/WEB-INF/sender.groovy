import de.michaelkuerbis.presenter.servlets.SettingsServlet;
import de.michaelkuerbis.presenter.utils.CastConnection;

html.div {
	div( class:"container", id:"sender", style:"display:none;"){
		div(class:"col-xs-12"){
			div( class:"page-header"){ h1('data-i18n':"sender_title",'start cast') }
		}
		div(class:"col-xs-12"){
			div(class:"form-horizontal"){

				div( class:"form-group"){
					label(class:"col-sm-2 control-label",'data-i18n':"sender_receiver", "Receiver:")
					div( class:"col-sm-10"){
						select(id:"receiver-ip", class:"form-control"){
							for(CastConnection con: SettingsServlet.getConnections()){
								option(value:con.getIp(),"data-default":con.isDefault,con.getName())
							}
						}
					}
					label(class:"col-sm-2 control-label",'data-i18n':"sender_all", "All (cast with default property):")
					div( class:"col-sm-10"){
						input( type:"checkbox",class:"form-control", id:"all")
					}
				}

				div( class:"form-group"){
					label(class:"col-sm-2 control-label",'data-i18n':"sender_url", "Url:")
					div( class:"col-sm-10"){
						input( type:"text",class:"form-control", id:"url",'data-i18n-placeholder':"sender_url_placeholder", placeholder:"http://www....")
					}
				}
				div( class:"form-group"){
					div( class:"col-sm-offset-2 col-sm-10"){
						button( id:"launch", class:"btn btn-default",'data-i18n':"sender_launch","launch")
					}
				}
			}
			div(class:"form-horizontal"){
				div( class:"form-group"){
					label(class:"col-sm-2 control-label",'data-i18n':"sender_reload", "Reload page after sec.")
					div( class:"col-sm-10"){
						input( type:"number",class:"form-control", id:"refresh", min:"0", value:"0")
					}
					label(class:"col-sm-offset-2 col-sm-10 pull-left",'data-i18n':"sender_zero_reload", "0 means no reload at all")
				}
			}
		}
	}
	script(src:"assets/js/sender.js")
}