module(..., package.seeall)

local widget = require "widget" ;

function addTableList(view, myW, myH, myX,myY, rowRender, rowTouch)
	local newTab = widget.newTableView(
    {
        left = myX,
        top = myY,
        height = myH,
        width = myW,
        onRowRender = rowRender,
        onRowTouch = rowTouch,
    });
    
	newTab.x = myX;
	newTab.y = myY;
	
	view:insert(newTab);
	
	return newTab;
end

function addMap(view, myW, myH, myX,myY, latitude, longitude)
	local myMap = native.newMapView( myX, myY, myW, myH );
	
	myMap.x = myX;
	myMap.y = myY;
	
	-- Display map as vector drawings of streets (other options are "satellite" and "hybrid")
	myMap.mapType = "standard";
	myMap:setCenter(latitude, longitude);
	view:insert(myMap);
	
	return myMap;
end

function addLabel(view, valor,myW, myH, myX,myY, acao)
	local minhaFonte = native.newFont( "fonts/Helvetica.ttf", 12);
    local options = {
        text = valor,
        x = myX,
        y = myY,
        width = myW,
        height = myH,
        font = minhaFonte,   
        align = "left"
   }
    -- Label...
   local displayText = display.newText( options);
   displayText:setFillColor( 0, 0, 0 );
   
   
   displayText.x = myX - 50;
   displayText.y = myY;
   displayText.align = "left";
   
   
   displayText:addEventListener( "tap", acao );
   
   view:insert(displayText);
   
   return displayText;
end


function addLabelTable(view, valor,myW, myH, myX,myY)
	local minhaFonte = native.newFont( "fonts/Helvetica.ttf", 10);
    local options = {
        text = valor,
        x = myX,
        y = myY,
        width = myW,
        height = myH,
        font = minhaFonte,   
        align = "left"
   }
    -- Label...
   local displayText = display.newText( options);
   displayText:setFillColor( 0, 0, 0 );
   
   displayText.x = myX;
   displayText.y = myY;
   
   displayText.align = "left";
   
   view:insert(displayText);
   
   return displayText;
end

function addButton(view, valor, myW, myH, myX, myY, acao)
    
    local btnConsulta = widget.newButton(
    {
        label = valor,
        onEvent = acao,
        emboss = false,
        -- Properties for a rounded rectangle button
        shape = "roundedRect",
        width = myW,
        height = myH,
        cornerRadius = 2,
        fillColor = { default={0.9,0.9,0.9,1}, over={0.7,0.7,0.7,0.4} },
        strokeColor = { default={0.9,0.9,0.9,1}, over={0.8,0.8,1,1} },
        strokeWidth = 4
    });

	btnConsulta.x = myX+60;
	btnConsulta.y = myY;
	
	
	if ( myX > myW) then
        btnConsulta.x = (myX - myW) + ((myX - myW) *0.8);
    else
        btnConsulta.x = myX + (myW * 0.45)  ;
    end
    
        
    if (myY > myH) then
        btnConsulta.y = myY + (myH *0.5) ;
    else
        btnConsulta.y = myY + (myH *0.5);
    end
    
    if (myW == display.contentWidth) then
    	btnConsulta.width = myW;	
    end
    
	-- Change the button's label text
	btnConsulta:setLabel( valor );
	
    btnConsulta:addEventListener( "tap", acao );
    view:insert(btnConsulta);
    
    return btnConsulta;
end

function addImage(view, imagem, myW, myH, myX, myY, acao)
    
     --local btnConsulta = display.newImageRect(imagem, myW, myH);
    local btnConsulta = display.newImage(imagem);
    
    if ( myX > myW) then
        btnConsulta.x = (myX - myW) + ((myX - myW) *0.8);
    else
        btnConsulta.x = myX + (myW * 0.33)  ;--(myW - myX) / 2;
    end
    
        
    if (myY > myH) then
        btnConsulta.y = myY + (myH *0.5) ;
    else
        btnConsulta.y = myY + (myH *0.5);
    end
    
    if (myW == display.contentWidth) then
    	btnConsulta.width = myW;	
    end
    
    
    btnConsulta:addEventListener( "tap", acao )
    view:insert(btnConsulta);
    
    return btnConsulta;
end

function addTextbox(view, valor, myW, myH, myX, myY, scrollY, isScrollView)
    local minhaFonte = native.newFont( "fonts/Helvetica.ttf", 12);
    
    objText = {};
    objText.textbox = native.newTextField( 150, 150, myW, myH );
    --objText.textbox.hasBackground = false;
    objText.textbox.x = myX+15;
    objText.textbox.y = myY;
    objText.view = view;
    objText.positionY = view.y;
    objText.myScrollY = scrollY;
    objText.isScrollView = isScrollView;
    
    if (objText.isScrollView) then
    	if objText.myScrollY ~= nil then
    		objText.scrollY = objText.myScrollY;
    		objText.positionY = view.top;
    	end 
    end
    
    objText.textFieldHandler = function(event)
        if ( event.phase == "began" ) then
           	if (objText.isScrollView) then
           		if objText.myScrollY ~= nil then
	           		objText.view:scrollToPosition
		            {
		                y = objText.myScrollY,
		                time = 300
		            } ;
	            end
           	else
           		if objText.myScrollY ~= nil then
	       			transition.to(objText.view,{time=300, y=objText.myScrollY});
	       		end
           	end
        elseif ( event.phase == "ended" or event.phase == "submitted" ) then
            
           native.setKeyboardFocus( nil );
           if (objText.isScrollView) then
           		if objText.myScrollY ~= nil then
	           		objText.view:scrollToPosition
		            {
		                y = objText.positionY,
		                time = 300
		            } ;
	            end
	       else
	       		if objText.myScrollY ~= nil then
	       			transition.to(objText.view,{time=300, y=objText.positionY});
	       		end
	       end
	       
        elseif ( event.phase == "editing" ) then
        end
     end
     
     
     objText.textbox.font = minhaFonte;
     
     objText.textbox:addEventListener( "userInput", objText.textFieldHandler )
     objText.textbox.align = "left";
     objText.view:insert(objText.textbox);
     
     --[[
     objText.linhaDigitar = display.newImageRect("imagens/caixatexto.png",279,34);
     objText.linhaDigitar.x = myX-25;
     objText.linhaDigitar.y = myY;
     ]]--
     
     --objText.view:insert(objText.linhaDigitar);
   
     return objText.textbox;
    
end

function addCombo(view, myW, myH, myX, myY, textoInicial, textoConfirmarSelecao, tabela)
    
    local obj = {};
    
    obj.x = myX;
    obj.y = myY;
    
    obj.minhaFonte = native.newFont( "fonts/Helvetica.ttf", 12);
    
    obj.view = view;
    obj.tabela = tabela ;
    obj.textoInicial = textoInicial;
    obj.textoConfirmarSelecaoTxt = textoConfirmarSelecao;
    obj.valor = nil;
    
    obj.callBackMostrarOpcoes = nil;
    obj.callBackConfirmarSelecao = nil;
    
    -- Carregando tabela descricao
    obj.tabelaItens = {};
    local total = table.getn(obj.tabela);
    for i = 1,total do
        obj.tabelaItens[i] = obj.tabela[i].descricao;
    end
    
    -- Funcoes...
    obj.confirmarSelecao = function (event)
        
        if obj.listaOpcoes == nil then
            return;
        end
        
        local valores = obj.listaOpcoes:getValues();
        
        local total = table.getn(obj.tabela);
        for i = 1,total do
            if obj.tabela[i].descricao == valores[1].value then
                obj.valor = obj.tabela[i].codigo;
                obj.textoInicial = obj.tabela[i].descricao;
            end
        end

        display.remove(obj.listaOpcoes);
        display.remove(obj.textoConfirmarSelecao);
        
        obj.listaOpcoes = nil;
        obj.textoConfirmarSelecao = nil;
        
        obj.iniciar();
        
        if obj.callBackConfirmarSelecao ~= nil then
             obj.callBackConfirmarSelecao();
         end
         
    end
    
    obj.mostrarOpcoes = function (event)
         if obj.listaOpcoes ~= nil then
            return;
         end
         
         if obj.callBackMostrarOpcoes ~= nil then
             obj.callBackMostrarOpcoes();
         end
         
         obj.comboBox.alpha = 0;
         obj.labelComboBox.alpha = 0;
         
         display.remove(obj.comboBox);
         display.remove(obj.labelComboBox);
         
         local indexLista = 1;
         
         if obj.valor ~= nil then
            indexLista = obj.valor;
         end 
         -- Mostrar opcoes da lista...
         obj.listaOpcoes = widget.newPickerWheel({
            fontSize = 16,
            left=0,
            top = obj.y+20,
            columns = {{ 
                align = "left",
                width = display.contentWidth,
                heigth=40,
                startIndex = indexLista,
                labels = obj.tabelaItens
            }}
        });

        obj.view:insert(obj.listaOpcoes);

        local options2 = {
            text = obj.textoConfirmarSelecaoTxt,
            x = 10,
            y = obj.y,
            width = display.contentWidth,
            height = 0,
            font=obj.minhaFonte,
            align = "left"
         }
        -- Label...
       obj.textoConfirmarSelecao = display.newText( options2);
       obj.textoConfirmarSelecao.font = obj.minhaFonte;
       obj.textoConfirmarSelecao.x = obj.x+65;
       obj.textoConfirmarSelecao.y = obj.y+5;
       obj.textoConfirmarSelecao:setFillColor( 0, 0, 1 );

       obj.textoConfirmarSelecao:addEventListener( "tap", obj.confirmarSelecao );

       obj.view:insert(obj.textoConfirmarSelecao);  
    end
    -- Fim Funcoes...
    obj.iniciar = function()
        obj.comboBox = display.newImageRect("images/combo.png",myW,myH);
        obj.comboBox.x = myX+10;
        obj.comboBox.y = myY;

        obj.comboBox:addEventListener( "tap", obj.mostrarOpcoes );

        obj.view:insert(obj.comboBox);

       local options2 = {
           text = obj.textoInicial,
           x = myX,
           y = myY,
           width = myW,
           height = myH,
           font=obj.minhaFonte,
           align = "left"
        }
       -- Label...
       obj.labelComboBox = display.newText( options2);
       obj.labelComboBox.font = obj.minhaFonte;
       obj.labelComboBox.x = myX+20;
       obj.labelComboBox.y = myY+10;
       obj.labelComboBox:setFillColor( 0, 0, 0 );

       obj.labelComboBox:addEventListener( "tap", obj.mostrarOpcoes );

       obj.view:insert(obj.labelComboBox);
   end
   
   obj.iniciar();
   
   return obj;
    
end


function convertHexToRGB(hexCode)
   assert(#hexCode == 7, "The hex value must be passed in the form of #XXXXXX");
   local hexCode = hexCode:gsub("#","")
   return tonumber("0x"..hexCode:sub(1,2))/255,tonumber("0x"..hexCode:sub(3,4))/255,tonumber("0x"..hexCode:sub(5,6))/255;
end

function convertRGB(r, g, b)
   assert(r and g and b and r <= 255 and r >= 0 and g <= 255 and g >= 0 and b <= 255 and b >= 0, "You must pass all 3 RGB values within a range of 0-255");
   return r/255, g/255, b/255;
end
