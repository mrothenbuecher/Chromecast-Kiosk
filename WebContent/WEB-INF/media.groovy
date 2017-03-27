html.div {
	style("""
		.btn-file {
		    position: relative;
		    overflow: hidden;
		}
		.btn-file input[type=file] {
		    position: absolute;
		    top: 0;
		    right: 0;
		    min-width: 100%;
		    min-height: 100%;
		    font-size: 100px;
		    text-align: right;
		    filter: alpha(opacity=0);
		    opacity: 0;
		    outline: none;
		    background: white;
		    cursor: inherit;
		    display: block;
		}
	""")
	div( class:"container", id:"media", style:"display:none;"){
		div(class:"col-xs-12"){
			div( class:"page-header"){ h1('data-i18n':"media_title",'media') }
		}
		div(class:"col-xs-12"){
			label( class:"btn btn-default btn-file"){
				div("data-i18n":"media_browse","Browse")
				input(id:"media-file", type:"file", "hidden":"hidden")
			}
			button(class:"btn btn-info", id:"media-upload",'data-i18n':"media_upload","upload")
		}
		div(class:"col-xs-12"){ hr('') }
		div(class:"col-xs-12"){
			label('data-i18n':"media_label","File:")
			select(class:"form-control media-list", id:"media-del-list"){
				option("")
			}
			button(class:"btn btn-default",id:"media-refresh",'data-i18n':"media_refresh", "refresh")
			button(class:"btn btn-default",id:"media-delete",'data-i18n':"media_delete", "delete")
		}
	}
	script(src:"assets/js/media.js")
}