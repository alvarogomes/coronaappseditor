local storyboard = require "composer"

local scene = storyboard.newScene()

local mainGroup = display.newGroup();
display.setStatusBar( display.HiddenStatusBar );

-- Main function
local function main()
        
        
        storyboard.gotoScene("#viewmain" )
        
        return true
end 

scene:addEventListener( "create", scene )

main()

