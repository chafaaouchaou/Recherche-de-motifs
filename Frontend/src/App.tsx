import { useState, useEffect } from 'react';
import { BarChart, XAxis, YAxis, Bar, Tooltip, Legend } from 'recharts';
import { Check, Loader2, Download } from 'lucide-react';
import ReactMarkdown from 'react-markdown';
import './App.css';

// Types
interface Algorithm {
  id: number;
  name: string;
}

interface WordResult {
  word: string;
  occurrences: number;
}

interface SearchResponse {
  totalWords: number;
  executionTime: number;
  wordResults: WordResult[];
}

interface AlgorithmResult {
  algorithmNumber: number;
  algorithmName: string;
  searchResponse: SearchResponse;
}

interface SearchResult {
  generatedText: string;
  results: AlgorithmResult[];
  aianalysis: string;
}

const algorithms: Algorithm[] = [
  // { id: 1, name: "Recherche linéaire" },
  { id: 2, name: "Boyer-Moore" },
  { id: 3, name: "Horspool" },
  { id: 4, name: "Quick Search" },
  { id: 5, name: "Naive Search Strncmp" },
  { id: 6, name: "Naive Search, Strncmp, boucle rapide" },
  { id: 7, name: "Naive Search, Strncmp, boucle rapide, sentinelle" },
  { id: 8, name: "Morris-Pratt" },
  { id: 9, name: "Knuth-Morris-Pratt" },
  { id: 10, name: "Naive Internal With Fast Loop With Sentinel" },
  { id: 11, name: "Naive Internal With Fast Loop" },
  { id: 12, name: "Naive Internal" }
];

const App = () => {
  const [textSize, setTextSize] = useState<number>(100000); 
  const [alphabetSize, setAlphabetSize] = useState<number>(4); 
  const [selectedAlgos, setSelectedAlgos] = useState<number[]>([2, 3, 4, 12]); 
  const [words, setWords] = useState<string[]>(["abc", "bcd", "aaa"]); 
  const [newWord, setNewWord] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(false);
  const [initialLoading, setInitialLoading] = useState<boolean>(true);
  const [result, setResult] = useState<SearchResult | null>(null);


  const performSearch = async (isInitialLoad = false) => {
    // Si c'est une recherche manuelle (pas le chargement initial), on active le loading
    if (!isInitialLoad) {
      setLoading(true);
    }
    
    try {
      const response = await fetch('https://pattern-matching-api.chafaaouchaou.online/api/search/text', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          tailleTexte: textSize,
          tailleAlphabet: alphabetSize,
          algoNumber: selectedAlgos,
          words,
        }),
      });
      const data = await response.json();
      setResult(data);
    } catch (error) {
      console.error('Error:', error);
    } finally {
      // On désactive le loading approprié selon le type d'appel
      if (isInitialLoad) {
        setInitialLoading(false);
      } else {
        setLoading(false);
      }
    }
  };

  // Appel API au chargement de la page
  useEffect(() => {
    performSearch(true);
  }, []);

  const handleWordAdd = () => {
    if (newWord && !words.includes(newWord)) {
      setWords([...words, newWord]);
      setNewWord("");
    }
  };

  const handleAlgoToggle = (id: number) => {
    if (selectedAlgos.includes(id)) {
      setSelectedAlgos(selectedAlgos.filter(algo => algo !== id));
    } else {
      setSelectedAlgos([...selectedAlgos, id]);
    }
  };

  const handleSearch = () => {
    performSearch(false); // Indiquer que ce n'est pas le chargement initial
  };

  const handleDownloadText = () => {
    if (!result) return;
    
    const element = document.createElement('a');
    const file = new Blob([result.generatedText], {type: 'text/plain'});
    element.href = URL.createObjectURL(file);
    element.download = 'generated.txt';
    document.body.appendChild(element);
    element.click();
    document.body.removeChild(element);
  };

  // Composant pour le skeleton du graphique avec dimensions fixes
  const ChartSkeleton = () => (
    <div className="animate-pulse" style={{ width: '800px', height: '400px' }}>
      {/* En-tête simulé */}
      
      {/* Squelette du graphique */}
      <div className="h-80 w-full bg-gray-200 rounded-lg relative">
        {/* Simuler quelques barres */}
        <div className="absolute bottom-0 left-8 w-12 h-40 bg-gray-300 rounded-t"></div>
        <div className="absolute bottom-0 left-32 w-12 h-60 bg-gray-300 rounded-t"></div>
        <div className="absolute bottom-0 left-56 w-12 h-24 bg-gray-300 rounded-t"></div>
        <div className="absolute bottom-0 left-80 w-12 h-52 bg-gray-300 rounded-t"></div>
        <div className="absolute bottom-0 left-104 w-12 h-40 bg-gray-300 rounded-t"></div>
        <div className="absolute bottom-0 left-128 w-12 h-60 bg-gray-300 rounded-t"></div>
        <div className="absolute bottom-0 left-152 w-12 h-24 bg-gray-300 rounded-t"></div>
        <div className="absolute bottom-0 left-176 w-12 h-52 bg-gray-300 rounded-t"></div>
      </div>
      
      {/* Légende simulée */}
    </div>
  );





  // Composant pour le skeleton du tableau
  const TableSkeleton = () => (
    <div className="overflow-hidden shadow-sm rounded-lg border border-gray-200 animate-pulse">
      <div className="bg-gray-100 p-3">
        <div className="grid grid-cols-4 gap-4">
          <div className="h-6 bg-gray-200 rounded"></div>
          <div className="h-6 bg-gray-200 rounded"></div>
          <div className="h-6 bg-gray-200 rounded"></div>
          <div className="h-6 bg-gray-200 rounded"></div>
        </div>
      </div>
      {Array(5).fill(0).map((_, index) => (
        <div key={index} className={`p-4 ${index % 2 === 0 ? 'bg-white' : 'bg-gray-50'}`}>
          <div className="grid grid-cols-4 gap-4">
            <div className="h-6 bg-gray-200 rounded"></div>
            <div className="h-6 bg-gray-200 rounded"></div>
            <div className="h-6 bg-gray-200 rounded"></div>
            <div className="h-6 bg-gray-200 rounded"></div>
          </div>
        </div>
      ))}
    </div>
  );

  // Composant pour le skeleton de l'analyse AI
  const AiAnalysisSkeleton = () => (
    <div className="animate-pulse space-y-3">
      <div className="h-6 bg-gray-200 rounded w-3/4"></div>
      <div className="h-6 bg-gray-200 rounded w-full"></div>
      <div className="h-6 bg-gray-200 rounded w-5/6"></div>
      <div className="h-6 bg-gray-200 rounded w-4/5"></div>
    </div>
  );

  return (
    <div className="flex h-screen bg-gray-50">
      {/* Sidebar - widened to 72 from 64 */}
      <div className="w-72 bg-white p-6 shadow-md">
        <h2 className="text-xl font-bold mb-6 text-gray-800">Settings</h2>
        
        <div className="space-y-8">
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-700">
              Text size: {textSize}
            </label>
            <input
              type="range"
              min="5000"
              max="1000000"
              step="1000"
              value={textSize}
              onChange={(e) => setTextSize(Number(e.target.value))}
              className="w-full h-2 bg-gray-200 rounded-lg appearance-none cursor-pointer"
            />
          </div>
    
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-700">
              Alphabet size: {alphabetSize}
            </label>
            <input
              type="range"
              min="2"
              max="206"
              step="1"
              value={alphabetSize}
              onChange={(e) => setAlphabetSize(Number(e.target.value))}
              className="w-full h-2 bg-gray-200 rounded-lg appearance-none cursor-pointer"
            />
          </div>
    
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-700">Mots à rechercher</label>
            <div className="flex space-x-2 mb-2">
              <input
                type="text"
                value={newWord}
                onChange={(e) => setNewWord(e.target.value)}
                placeholder="Nouveau mot"
                className="flex-1 px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
              <button
                onClick={handleWordAdd}
                className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 transition-colors"
              >
                +
              </button>
            </div>
            <div className="flex flex-wrap gap-2">
              {words.map((word, index) => (
                <span key={index} className="px-2 py-1 bg-blue-100 rounded-md text-sm">
                  {word}
                  <button
                    className="ml-2 text-red-500"
                    onClick={() => setWords(words.filter((_, i) => i !== index))}
                  >
                    ×
                  </button>
                </span>
              ))}
            </div>
          </div>
    
          <div>
            <label className="block text-sm font-medium mb-2 text-gray-700">Algorithms</label>
            <div className="space-y-2 max-h-48 overflow-y-auto">
              {algorithms.map((algo) => (
                <div
                  key={algo.id}
                  className="flex items-center space-x-2 cursor-pointer"
                  onClick={() => handleAlgoToggle(algo.id)}
                >
                  <div className={`w-4 h-4 border rounded flex items-center justify-center transition-colors
                    ${selectedAlgos.includes(algo.id) ? 'bg-blue-500 border-blue-500' : 'border-gray-300'}`}>
                    {selectedAlgos.includes(algo.id) && <Check className="w-3 h-3 text-white" />}
                  </div>
                  <span className="text-sm text-gray-700">{algo.name}</span>
                </div>
              ))}
            </div>
          </div>
    
          <button
            className={`w-full px-4 py-2 rounded-md text-white shadow-sm transition-colors
              ${loading || initialLoading || words.length === 0 || selectedAlgos.length === 0 
                ? 'bg-gray-400 cursor-not-allowed' 
                : 'bg-blue-500 hover:bg-blue-600'}`}
            onClick={handleSearch}
            disabled={loading || initialLoading || words.length === 0 || selectedAlgos.length === 0}
          >
            {loading ? (
              <div className="flex items-center justify-center">
                <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                Loading...
              </div>
            ) : (
              'Rechercher'
            )}
          </button>
        </div>
      </div>
    
      {/* Main Content */}
      <div className="flex-1 p-8 overflow-auto">
        {initialLoading ? (
          <div className="space-y-8">
            {/* Skeleton pour le texte généré */}
            <div className="bg-white rounded-xl shadow-sm p-6 transition-shadow hover:shadow-md">
              <h3 className="text-lg font-semibold mb-4 text-gray-800">Generated text</h3>
              <div className="animate-pulse h-10 w-48 bg-gray-200 rounded-md"></div>
            </div>
            
            {/* Skeleton pour les résultats de recherche */}
            <div className="bg-white rounded-xl shadow-sm p-6 transition-shadow hover:shadow-md">
              <h3 className="text-lg font-semibold mb-6 text-gray-800">Search results</h3>
              <div className="mb-8 flex justify-center">
                <ChartSkeleton />
              </div>
              <TableSkeleton />
            </div>
            
            {/* Skeleton pour l'analyse AI */}
            <div className="bg-white rounded-xl shadow-sm p-6 transition-shadow hover:shadow-md">
              <h3 className="text-lg font-semibold mb-4 text-gray-800">AI analysis</h3>
              <AiAnalysisSkeleton />
            </div>
          </div>
        ) : (
          <div className="space-y-8">
            <div className="bg-white rounded-xl shadow-sm p-6 transition-shadow hover:shadow-md">
              <h3 className="text-lg font-semibold mb-4 text-gray-800">Generated text</h3>
              <button
                onClick={handleDownloadText}
                className="flex items-center space-x-2 px-6 py-3 bg-emerald-500 text-white rounded-md hover:bg-emerald-600 focus:outline-none focus:ring-2 focus:ring-emerald-500 transition-colors shadow-sm"
              >
                <Download className="w-5 h-5" />
                <span>Download the generated text</span>
              </button>
            </div>
    
            <div className="bg-white rounded-xl shadow-sm p-6 transition-shadow hover:shadow-md">
              <h3 className="text-lg font-semibold mb-6 text-gray-800">Search results</h3>
              <div className="mb-8 flex justify-center">
                {loading ? (
                  <ChartSkeleton />
                ) : result && result.results && result.results.length > 0 ? (
                  <BarChart
                    width={800}
                    height={400}
                    data={result.results.map(r => ({
                      name: r.algorithmName,
                      'Execution time': r.searchResponse.executionTime,
                    }))}
                    margin={{ top: 20, right: 30, left: 20, bottom: 100 }}
                  >
                    <XAxis dataKey="name" angle={-45} textAnchor="end" height={100} />
                    <YAxis />
                    <Tooltip contentStyle={{ borderRadius: '8px', boxShadow: '0 2px 10px rgba(0,0,0,0.1)' }} />
                    <Legend wrapperStyle={{ paddingTop: '10px' }} />
                    <Bar dataKey="Execution time" fill="#3b82f6" radius={[4, 4, 0, 0]} />
                  </BarChart>
                ) : (
                  <div className="text-center text-gray-500">Aucune donnée disponible</div>
                )}
              </div>
    
              {loading ? (
                <TableSkeleton />
              ) : result && result.results && result.results.length > 0 ? (
                <div className="overflow-hidden shadow-sm rounded-lg border border-gray-200">
                  <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                      <tr>
                        <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Algorithm</th>
                        <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Total occurrences</th>
                        <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Execution time</th>
                        <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Details</th>
                      </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                      {result.results.map((algResult, index) => (
                        <tr key={index} className={index % 2 === 0 ? 'bg-white' : 'bg-gray-50'}>
                          <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-800">{algResult.algorithmName}</td>
                          <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{algResult.searchResponse.totalWords}</td>
                          <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{algResult.searchResponse.executionTime}ms</td>
                          <td className="px-6 py-4 text-sm text-gray-500">
                            <div className="space-y-1">
                              {algResult.searchResponse.wordResults.map((wordResult, idx) => (
                                <p key={idx}>
                                  "{wordResult.word}": {wordResult.occurrences} occurrences
                                </p>
                              ))}
                            </div>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              ) : (
                <div className="text-center text-gray-500 p-8 border border-gray-200 rounded-lg">
                  Aucune donnée disponible
                </div>
              )}
            </div>
    
            <div className="bg-white rounded-xl shadow-sm p-6 transition-shadow hover:shadow-md">
              <h3 className="text-lg font-semibold mb-4 text-gray-800">AI analysis</h3>
              <div className="prose max-w-none">
                {loading ? (
                  <AiAnalysisSkeleton />
                ) : result && result.aianalysis ? (
                  <ReactMarkdown>{result.aianalysis}</ReactMarkdown>
                ) : (
                  <div className="text-center text-gray-500">Aucune analyse disponible</div>
                )}
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default App;