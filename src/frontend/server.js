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
    const uploadedPath = req.file.path;
    const algorithm = req.body.algorithm;
    const heuristic = req.body.heuristic;
    const targetPath = path.join(inputPath, 'test/input', 'qwertyuiop.txt');

    fs.renameSync(uploadedPath, targetPath);

    try {
        const filepath = path.join('../..');
        const compileCommand = "javac -d bin -sourcepath src/backend src/backend/Main.java  ";

        const command = 
          `cd ${filepath} && 
          ${compileCommand} && 
          cd bin &&
          java Main ${algorithm} ${heuristic}`;

        // execute command (need testing)
        const output = execSync(command, { encoding: 'utf-8' });

        console.log("Java output:\n", output);
        res.json({ status: 'success', message: 'Java executed' });
    } catch (err) {
        console.error("Java execution error:\n", err.toString());
        res.status(500).json({ status: 'error', error: err.toString() });
    }
});

// listening
app.listen(PORT, () => {
    console.log(`Server running at http://localhost:${PORT}`);
});
