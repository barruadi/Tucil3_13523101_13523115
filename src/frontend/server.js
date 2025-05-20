const express = require('express');
const multer = require('multer');
const path = require('path');
const { execSync } = require('child_process');
const fs = require('fs');
const cors = require('cors');

const app = express();
app.use(cors({
    origin: 'http://localhost:3000'
}));

const PORT = 3001;

const inputPath = path.dirname(path.dirname(__dirname))

const upload = multer({
    dest: path.join(
        inputPath, 
        'test/input'
    )
});

app.use(express.static(path.join(inputPath, 'test/public')));

app.post('/upload', upload.single('file'), (req, res) => {
    console.log("File:", req.file);
    console.log("Body:", req.body);
    const uploadedPath = req.file.path;
    const algorithm = req.body.algorithm;
    const heuristic = req.body.heuristic;
    const targetPath = path.join(inputPath, 'test/input', 'qwertyuiop.txt');

    fs.renameSync(uploadedPath, targetPath);

    try {
        const filepath = path.resolve(__dirname, "../.."); // absolute path

        const compileCommand = `javac -d bin -sourcepath src/backend src/backend/Main.java`;
        const runCommand = `java Main ${algorithm} ${heuristic}`;
        const fullCommand = `cd "${filepath}" && ${compileCommand} && cd bin && ${runCommand}`;

        const output = execSync(fullCommand, { encoding: "utf-8" });

        console.log("Java output:\n", output);
        res.json({ status: 'success', message: 'Java executed' });
    } catch (err) {
        console.error("Java execution error:\n", err.toString());
        res.status(500).json({ status: 'error', error: err.toString() });
    }
});

app.get('/solution', (req, res) => {
    const solutionPath = path.join(inputPath, 'src/frontend/test/public/solution.json');
    console.log("Solution path:", solutionPath);
    if (fs.existsSync(solutionPath)) {
        fs.readFile(solutionPath, 'utf8', (err, data) => {
            if (err) {
                console.error("Error reading solution file:", err);
                return res.status(500).json({ error: 'Error reading solution file' });
            }
            try {
                const jsonData = JSON.parse(data);
                res.json(jsonData);
            } catch (parseErr) {
                console.error("Error parsing JSON:", parseErr);
                res.status(500).json({ error: 'Error parsing JSON' });
            }
        });
    } else {
        console.error("Solution file not found");
        res.status(404).json({ error: 'Solution file not found' });
    }
})

// listening
app.listen(PORT, () => {
    console.log(`Server running at http://localhost:${PORT}`);
});
