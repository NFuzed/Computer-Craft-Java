local computerId = os.getComputerID()

local function sendStartMessage(ws)
    -- Send a JSON message

    local content = {
        xPosition = 0,
        yPosition = 0,
        zPosition = 0,
        direction = "NORTH"
    }

    local message = {
        turtleId = computerId,
        commandId = -1,
        action = "Connect",
        success = true,
        content = content
    }
    ws.send(textutils.serializeJSON(message))
end

local function executeCommand(command)
    local func, err = load("return ".. command)
    if not func then
        func, err = load(command)
    end

    if func then
        local success, results = pcall(function()
            return table.pack(func())
        end)
        if success then
            return success, results, commandId
        else
            return false, "Error executing command: ".. results, commandId
        end
    else
        return false, "Error loading command: ".. err, commandId
    end
end

local function connectAndListen()
    local ws, err = http.websocket("ws://localhost:8887")
    if not ws then
        print("Connection failed: ".. err)
        return false
    else
        print("Connected to server")

        sendStartMessage(ws)

        local function listen()
            while true do
                local message = ws.receive()
                if message then
                    local jsonMessage = textutils.unserializeJSON(message)
                    local command = jsonMessage.command
                    local commandId = jsonMessage.commandId
                    print("Received: ".. command)

                    local success, results = executeCommand(command)

                    local responseResults = {}
                    if success and results then
                        for i = 1, results.n do
                            table.insert(responseResults, results[i])
                        end
                    end

                    local responseMessage = {
                        turtleId = computerId,
                        commandId = commandId,
                        action = "Command Response",
                        success = success,
                        content = success and responseResults or results
                    }
                    print(success)
                    ws.send(textutils.serializeJSON(responseMessage))
                else
                    print("Connection closed by server")
                    break
                end
            end
        end

        local status, err = pcall(listen)
        if not status then
            print("Error: ".. err)
        end

        ws.close()
        return false
    end
end

local function main()
    while true do
        if not connectAndListen() then
            print("Retrying in 5 seconds...")
            sleep(5)
        end
    end
end

main()