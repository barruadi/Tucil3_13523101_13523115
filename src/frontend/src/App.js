import { useEffect, useState } from 'react';
import PuzzleBoard from './components/Board';
import './App.css';

function App() {
  const [steps, setSteps] = useState([]);
  const [currentBlocks, setCurrentBlocks] = useState([]);
  const [boardSize, setBoardSize] = useState({ width: 6, height: 6 });
  const [stepIndex, setStepIndex] = useState(0);
  const [isPlaying, setIsPlaying] = useState(false);
  const [selectedFile, setSelectedFile] = useState(null);

  const loadSolution = () => {
    fetch('http://localhost:3000/solution.json')
      .then(res => res.json())
      .then(data => {
        const allSteps = data.steps;
        setSteps(allSteps);
        setStepIndex(0);
        if (allSteps.length > 0) {
          setCurrentBlocks(allSteps[0].boardState.blocks);
          setBoardSize({
            width: allSteps[0].boardState.width,
            height: allSteps[0].boardState.height
          });
        }
      });
  };

  useEffect(() => {
    if (!isPlaying || stepIndex >= steps.length - 1) return;

    const timer = setTimeout(() => {
      const nextStep = steps[stepIndex + 1];
      setCurrentBlocks(nextStep.boardState.blocks);
      setStepIndex(stepIndex + 1);
    }, 600);

    return () => clearTimeout(timer);
  }, [isPlaying, stepIndex, steps]);

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0]);
  };

  const handleStartAlgorithm = () => {
    if (!selectedFile) return alert("Please upload a puzzle file first.");

    const formData = new FormData();
    formData.append('file', selectedFile);

    fetch("http://localhost:3001/upload", {
      method: "POST",
      body: formData,
    })
    .then(res => {
      if (!res.ok) throw new Error("Failed to upload");
      return res.json();
    })
    .then((data) => {
      console.log("Upload response:", data); 
      loadSolution();
    })
    .catch(err => {
      alert("Error uploading file or starting algorithm.");
      console.error(err);
    });
  };

  return (
    <div className="App">
      <h1>Tucil3 - RushHour</h1>

      <PuzzleBoard blocks={currentBlocks} boardSize={boardSize} />

      <div className="controls">

        <button onClick={() => setIsPlaying(true)} disabled={stepIndex >= steps.length - 1}>
          Play
        </button>

        <button onClick={() => {
          setIsPlaying(false);
          const prev = Math.max(stepIndex - 1, 0);
          setStepIndex(prev);
          setCurrentBlocks(steps[prev].boardState.blocks);
        }}>Previous Move</button>

        <button onClick={() => {
          setIsPlaying(false);
          const next = Math.min(stepIndex + 1, steps.length - 1);
          setStepIndex(next);
          setCurrentBlocks(steps[next].boardState.blocks);
        }}>Next Move</button>

      </div>

      <div className="upload-section">
        <input type="file" accept=".txt" onChange={handleFileChange} />
        <button onClick={handleStartAlgorithm}>Start</button>
      </div>
    </div>
  );
}

export default App;
