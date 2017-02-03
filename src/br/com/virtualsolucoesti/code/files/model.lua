module(..., package.seeall)

local widget = require "widget" ;

local storyboard = require "composer"
storyboard.purgeOnSceneChange = true;
local scene = storyboard.newScene();

local lib_movieclip = require( "util.movieclip_201" );
local uix = require("util.uix");
widget.setTheme("widget_theme_ios7");

local viewGroup;
local scrollView;

#variaveis

#metodos

local function scrollListener( event )

    local phase = event.phase
    if ( phase == "began" ) then print( "Scroll view was touched" )
    elseif ( phase == "moved" ) then print( "Scroll view was moved" )
    elseif ( phase == "ended" ) then print( "Scroll view was released" )
    end

    -- In the event a scroll limit is reached...
    if ( event.limitReached ) then
        if ( event.direction == "up" ) then print( "Reached bottom limit" )
        elseif ( event.direction == "down" ) then print( "Reached top limit" )
        elseif ( event.direction == "left" ) then print( "Reached right limit" )
        elseif ( event.direction == "right" ) then print( "Reached left limit" )
        end
    end

    return true
end

function drawScreen()
	scrollView = widget.newScrollView(
        {
            top = 0,
            left = 5,
            width = display.contentWidth,
            height = display.contentHeight,
            horizontalScrollDisabled=true,
            verticalScrollDisabled=false,
            topPadding = 40, 
            bottomPadding = 40 ,
            listener = scrollListener
        }
    );
    
    scrollView:setScrollHeight( 10 );
    viewGroup:insert(scrollView);
    
	#objetos
	
end

function scene:create( event )
    display.setDefault("background", 1, 1, 1);
    
    viewGroup = self.view;
    
    drawScreen();
    
    return viewGroup;
end


function scene:destroy( event )
    
    --composer.removeScene( true );
    
    --[[ 
    Runtime:removeEventListener("enterFrame",movimentarCamera)
    Runtime:removeEventListener( "touch", onTouch )
    Runtime:removeEventListener( "system", onSystemEvent ); 
    ]]--
end

function scene:exitScene( event )
    --[[
    Runtime:removeEventListener("enterFrame",movimentarCamera)
    Runtime:removeEventListener( "touch", onTouch )
    Runtime:removeEventListener( "system", onSystemEvent );
    ]]-- 
end

function scene:hide( event )

    local sceneGroup = self.view
    local phase = event.phase

    if ( phase == "will" ) then
		
    elseif ( phase == "did" ) then
		#remover
    end
end

function scene:show( event )
    
    -- storyboard.purgeScene( "views.Loading" )
    local phase = event.phase

    if ( phase == "will" ) then
        #aparecer
        -- Code here runs when the scene is still off screen (but is about to come on screen)
    elseif ( phase == "did" ) then
        -- Code here runs when the scene is entirely on screen
    end
    
end

scene:addEventListener( "create", scene )
scene:addEventListener( "destroy", scene )
scene:addEventListener( "show", scene )
scene:addEventListener( "hide", scene )

-- Runtime:addEventListener( "touch", onTouch )
-- Runtime:addEventListener("enterFrame", movimentarCamera)

--[[
local function onSystemEvent( event )
   if (event.type == "applicationSuspend") then
      
   end
end;

Runtime:addEventListener( "system", onSystemEvent );
]]--


return scene;