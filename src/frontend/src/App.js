import { useEffect, useState } from 'react';
import PuzzleBoard from './components/Board';
import './App.css';

function App() {
  const [controlState, setControlState] = useState({
    steps: [],
    currentBlocks: [],
    boardSize: { width: 6, height: 6 },
    stepIndex: 0,
    isPlaying: false,
  });

  const [setupState, setSetupState] = useState({
    selectedFile: null,
    algorithm: "AStar",
    heuristic: "1",
  });

  const loadSolution = () => {
    fetch('http://localhost:3000/solution.json')
      .then(res => res.json())
      .then(data => {
        const allSteps = data.steps;
        setControlState(prev => ({
          ...prev,
          steps: allSteps,
          stepIndex: 0,
          currentBlocks: allSteps[0]?.boardState.blocks || [],
          boardSize: {
            width: allSteps[0]?.boardState.width || 6,
            height: allSteps[0]?.boardState.height || 6,
          },
        }));
      });
  };

  useEffect(() => {
    if (!controlState.isPlaying || controlState.stepIndex >= controlState.steps.length - 1) return;

    const timer = setTimeout(() => {
      const nextStep = controlState.steps[controlState.stepIndex + 1];
      setControlState(prev => ({
        ...prev,
        currentBlocks: nextStep.boardState.blocks,
        stepIndex: prev.stepIndex + 1,
      }));
    }, 600);

    return () => clearTimeout(timer);
  }, [controlState.isPlaying, controlState.stepIndex, controlState.steps]);

  const handleFileChange = (e) => {
    setSetupState(prev => ({
      ...prev,
      selectedFile: e.target.files[0],
    }));
  };

  const handleStartAlgorithm = () => {
    if (!setupState.selectedFile) {
      return alert("Please upload a puzzle file first.");
    }

    const formData = new FormData();
    formData.append('file', setupState.selectedFile);
    formData.append('algorithm', setupState.algorithm);
    formData.append('heuristic', setupState.heuristic);

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

      <PuzzleBoard blocks={controlState.currentBlocks} boardSize={controlState.boardSize} />

      <div className="controls">
        <button
          onClick={() => setControlState(prev => ({ ...prev, isPlaying: true }))}
          disabled={controlState.stepIndex >= controlState.steps.length - 1}
        >
          Play
        </button>

        <button onClick={() => {
          setControlState(prev => {
            const prevIndex = Math.max(prev.stepIndex - 1, 0);
            return {
              ...prev,
              isPlaying: false,
              stepIndex: prevIndex,
              currentBlocks: prev.steps[prevIndex].boardState.blocks,
            };
          });
        }}>
          Previous Move
        </button>

        <button onClick={() => {
          setControlState(prev => {
            const nextIndex = Math.min(prev.stepIndex + 1, prev.steps.length - 1);
            return {
              ...prev,
              isPlaying: false,
              stepIndex: nextIndex,
              currentBlocks: prev.steps[nextIndex].boardState.blocks,
            };
          });
        }}>
          Next Move
        </button>
      </div>

      <div className="upload-section">
        <input type="file" accept=".txt" onChange={handleFileChange} />
        <button onClick={handleStartAlgorithm}>Start</button>

        <select
          value={setupState.algorithm}
          onChange={(e) => setSetupState(prev => ({ ...prev, algorithm: e.target.value }))}
        >
          <option value="AStar">A*</option>
          <option value="bidirectional">Bidirectional</option>
          <option value="UCS">Uniform Cost Search</option>
          <option value="GBFS">Greedy Best First Search</option>
        </select>

        <select
          value={setupState.heuristic}
          onChange={(e) => setSetupState(prev => ({ ...prev, heuristic: e.target.value }))}
        >
          <option value="1">Menghitung jarak</option>
          <option value="2">Hitung jumlah block yang menghalangi</option>
        </select>
      </div>
    </div>
  );
}

export default App;
