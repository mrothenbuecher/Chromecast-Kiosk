import de.michaelkuerbis.presenter.servlets.SettingsServlet;
import de.michaelkuerbis.presenter.utils.CastConnection;

html.div {
	div( class:"container", id:"cron", style:"display:none;"){
		div(class:"col-xs-12"){
			div( class:"page-header"){ h1('data-i18n':"cron_title",'add cronjobs') }
		}
		div(class:"col-xs-12"){
			div(class:"form-horizontal"){

				div( class:"form-group"){
					label(class:"col-sm-2 control-label",'data-i18n':"cron_desc", "Description:")
					div( class:"col-sm-10"){
						input( type:"text",class:"form-control", id:"name",'data-i18n-placeholder':"cron_desc_placeholder", placeholder:"description")
					}
				}

				div( class:"form-group"){
					label(class:"col-sm-2 control-label",'data-i18n':"cron_receiver", "Receiver:")
					div( class:"col-sm-10"){
						select(id:"receiver-ip", class:"form-control"){
							for(CastConnection con: SettingsServlet.getConnections()){
								option(value:con.getIp(),"data-default":con.isDefault,con.getName())
							}
						}
					}
				}

				div( class:"form-group"){
					label(class:"col-sm-2 control-label",'data-i18n':"cron_pattern", "Execution pattern:")
					div( class:"col-sm-10"){
						input( type:"text",class:"form-control", id:"pattern",'data-i18n-placeholder':"cron_pattern_placeholder", placeholder:"* * * * *")
					}
				}

				div( class:"form-group"){
					label(class:"col-sm-2 control-label",'data-i18n':"cron_url", "Url:")
					div( class:"col-sm-10"){
						input( type:"text",class:"form-control", id:"url",'data-i18n-placeholder':"cron_url_placeholder", placeholder:"http://www....")
					}
				}
				div( class:"form-group"){
					div( class:"col-sm-offset-2 col-sm-10"){
						button( id:"add", class:"btn btn-default",'data-i18n':"cron_add","add")
					}
				}
			}
			div(class:"form-horizontal"){
				div( class:"form-group"){
					label(class:"col-sm-2 control-label",'data-i18n':"cron_reload", "Reload page after sec.")
					div( class:"col-sm-10"){
						input( type:"number",class:"form-control", id:"refresh", min:"0", value:"0")
					}
					label(class:"col-sm-offset-2 col-sm-10 pull-left",'data-i18n':"cron_zero_reload", "0 means no reload at all")
				}
			}
		}
		div(class:"col-xs-12"){
			div( class:"page-header"){
				h1(class:"pull-left",'data-i18n':"cron_current_jobs",'current cronjobs')
				div(style:"margin-left: 10px;padding-top: 20px;"){
					button(class:"btn btn-default", id:"refreshjobs", style:"height: 34px;"){
						span(class:"glyphicon glyphicon-refresh", " ")
					}
				}
			}
		}
		div(id:"currentjobs",class:"col-xs-12"){
		}
	}
	script(src:"assets/js/cron.js")
}